package com.oleg.coupons.logic;

import com.oleg.coupons.dal.ICouponsDal;
import com.oleg.coupons.dto.Coupon;
import com.oleg.coupons.dto.CouponToClient;
import com.oleg.coupons.dto.CouponsPageResult;
import com.oleg.coupons.entities.CouponEntity;
import com.oleg.coupons.enums.ErrorType;
import com.oleg.coupons.enums.UserType;
import com.oleg.coupons.exceptions.ApplicationException;
import com.oleg.coupons.utils.CommonValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CouponLogic {

    private ICouponsDal couponsDal;
    private CategoryLogic categoryLogic;
    private CompanyLogic companyLogic;

    @Autowired
    public CouponLogic(ICouponsDal couponsDal, CategoryLogic categoryLogic, CompanyLogic companyLogic) {
        this.couponsDal = couponsDal;
        this.categoryLogic = categoryLogic;
        this.companyLogic = companyLogic;
    }

    public int addCoupon(Coupon coupon) throws ApplicationException {
        validateCoupon(coupon);
        CouponEntity couponEntity = new CouponEntity(coupon);

        try {
            couponEntity = this.couponsDal.save(couponEntity);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to add coupon", e);
        }
        return couponEntity.getId();
    }

    public void updateCoupon(Coupon coupon) throws ApplicationException {
        validateCoupon(coupon);
        CouponEntity couponEntity = new CouponEntity(coupon);


        try {
            this.couponsDal.save(couponEntity);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to update coupon", e);
        }
    }

    public void deleteCoupon(int id) throws ApplicationException {
        if (!isCouponExist(id)) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the coupon to be deleted");
        }
        try {
            this.couponsDal.deleteById(id);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to delete coupon with id " + id, e);
        }
    }

    public CouponToClient getById(int id) throws ApplicationException {
        CouponToClient coupon = this.couponsDal.getById(id);
        if (coupon == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the coupon you were looking for");
        }
        return coupon;
    }

    public CouponsPageResult getByFilters(UserType userType, int page, Integer[] categoryIds, Integer[] companyIds, Float minPrice, Float maxPrice, String searchText) throws ApplicationException {
        int couponsPerPage = 12;

        int adjustedPage = page - 1;
        Pageable pageable = PageRequest.of(adjustedPage, couponsPerPage);
        Page<CouponToClient> couponsPage;

        searchText = searchText.toLowerCase();

        if(companyIds[0]==null){
            companyIds = companyLogic.getAllCompanyIds();
        }

        if(categoryIds[0]==null){
            categoryIds = categoryLogic.getAllCategoryIds();
        }

        if (userType == UserType.ADMIN || userType == UserType.COMPANY) {
            couponsPage = this.couponsDal.getAllByFilters(categoryIds, companyIds, minPrice, maxPrice, searchText, pageable);
        } else {
            couponsPage = this.couponsDal.getAvailableByFilters(categoryIds, companyIds, minPrice, maxPrice, searchText, pageable);
        }

        int totalPages = couponsPage.getTotalPages();
        List<CouponToClient> coupons = couponsPage.getContent();

        if (coupons == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the coupons you were looking for");
        }

        CouponsPageResult couponsPageResult = new CouponsPageResult(coupons, totalPages);
        return couponsPageResult;
    }

    public Float getMinPrice() throws ApplicationException {
        Float minPrice;
        try {
            minPrice = couponsDal.getMinPrice();
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to retrieve lowest coupon price", e);
        }
        return minPrice;
    }

    public Float getMinPriceByCompany(int companyId) throws ApplicationException {
        Float minPrice;
        try {
            minPrice = couponsDal.getMinPriceByCompany(companyId);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to retrieve lowest coupon price", e);
        }
        return minPrice;
    }

    public Float getMaxPrice() throws ApplicationException {
        Float maxPrice;
        try {
            maxPrice = couponsDal.getMaxPrice();
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to retrieve highest coupon price", e);
        }
        return maxPrice;
    }

    public Float getMaxPriceByCompany(int companyId) throws ApplicationException {
        Float maxPrice;
        try {
            maxPrice = couponsDal.getMaxPriceByCompany(companyId);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to retrieve highest coupon price", e);
        }
        return maxPrice;
    }


    private void validateCoupon(Coupon coupon) throws ApplicationException {
        validateCouponName(coupon.getName());
        validateCouponDescription(coupon.getDescription());
        validateCouponDates(coupon);
        validateCouponCategory(coupon.getCategoryId());
        validateCompanyId(coupon.getCompanyId());
        validateCouponAmount(coupon);
        validateCouponPrice(coupon.getPrice());
    }

    private void validateCouponDates(Coupon coupon) throws ApplicationException {
        if (coupon.getStartDate().after(coupon.getEndDate())) {
            throw new ApplicationException(ErrorType.COUPON_START_DATE_AFTER_END_DATE);
        }
        Date earliestAllowableStartDate = new Date(2011, 11, 11);
        if (!coupon.getStartDate().before(earliestAllowableStartDate)) {
            throw new ApplicationException(ErrorType.COUPON_START_DATE_TOO_EARLY);
        }
    }

    private void validateCompanyId(int companyId) throws ApplicationException {
        if (!this.companyLogic.isCompanyExist(companyId)) {
            throw new ApplicationException(ErrorType.COMPANY_NOT_EXIST);
        }
    }

    private void validateCouponPrice(float price) throws ApplicationException {
        if (price < 0) {
            throw new ApplicationException(ErrorType.COUPON_PRICE_INVALID);
        }
    }

    private void validateCouponAmount(Coupon coupon) throws ApplicationException {
        if (coupon.getAmount() < 0) {
            coupon.setIsAvailable(false);
            throw new ApplicationException(ErrorType.COUPON_AMOUNT_INVALID);
        }
    }

    private void validateCouponCategory(int categoryId) throws ApplicationException {
        if (!this.categoryLogic.isCategoryExist(categoryId)) {
            throw new ApplicationException(ErrorType.CATEGORY_DOES_NOT_EXIST);
        }
    }

    private void validateCouponDescription(String description) throws ApplicationException {
        int validationResult = CommonValidations.validateStringLength(description, 10, 100);
        if (validationResult == 1) {
            throw new ApplicationException(ErrorType.COUPON_DESCRIPTION_TOO_LONG);
        }
        if (validationResult == -1) {
            throw new ApplicationException(ErrorType.COUPON_DESCRIPTION_TOO_SHORT);
        }
    }

    private void validateCouponName(String name) throws ApplicationException {
        int validationResult = CommonValidations.validateStringLength(name, 2, 45);
        if (validationResult == 1) {
            throw new ApplicationException(ErrorType.COUPON_NAME_TOO_LONG);
        }
        if (validationResult == -1) {
            throw new ApplicationException(ErrorType.COUPON_NAME_TOO_SHORT);
        }
    }

    public boolean isCouponExist(int id) {
        return this.couponsDal.existsById(id);
    }

    public boolean isCouponAvailable(int id) {
        return this.couponsDal.isAvailable(id);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void makeExpiredCouponsUnavailable() {
        List<CouponEntity> expiredCoupons = this.couponsDal.getExpiredCoupons();

        for (int i = 0; i < expiredCoupons.size(); i++) {
            expiredCoupons.get(i).setIsAvailable(false);
            couponsDal.save(expiredCoupons.get((i)));
        }
    }
}

