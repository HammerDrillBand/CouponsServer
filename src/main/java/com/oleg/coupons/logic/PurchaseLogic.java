package com.oleg.coupons.logic;

import com.oleg.coupons.dal.IPurchasesDal;
import com.oleg.coupons.dto.CouponToClient;
import com.oleg.coupons.dto.Purchase;
import com.oleg.coupons.dto.PurchaseToClient;
import com.oleg.coupons.dto.PurchasesPageResult;
import com.oleg.coupons.entities.PurchaseEntity;
import com.oleg.coupons.enums.ErrorType;
import com.oleg.coupons.enums.UserType;
import com.oleg.coupons.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class PurchaseLogic {

    private IPurchasesDal purchasesDal;
    private CouponLogic couponLogic;
    private int purchasesPerPage = 20;
    private CategoryLogic categoryLogic;
    private CompanyLogic companyLogic;


    @Autowired
    public PurchaseLogic(IPurchasesDal purchasesDal, CouponLogic couponLogic, CategoryLogic categoryLogic, CompanyLogic companyLogic) {
        this.purchasesDal = purchasesDal;
        this.couponLogic = couponLogic;
        this.categoryLogic = categoryLogic;
        this.companyLogic = companyLogic;
    }

    @Transactional(rollbackFor = ApplicationException.class)
    public int addPurchase(Purchase purchase) throws ApplicationException {
        validatePurchase(purchase);
        validateCouponAvailability(purchase.getCouponId());
        updateAmount(purchase.getCouponId(), purchase.getAmount());
        PurchaseEntity purchaseEntity = new PurchaseEntity(purchase);
        try {
            purchaseEntity = this.purchasesDal.save(purchaseEntity);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to add purchase", e);
        }
        return purchaseEntity.getId();
    }

    public PurchaseToClient getById(int id) throws ApplicationException {
        PurchaseToClient purchase = this.purchasesDal.getById(id);
        if (purchase == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the purchase you were looking for");
        }
        return purchase;
    }

    public PurchasesPageResult getByFilters(int page, Integer[] companyIds, Integer[] categoryIds, UserType userType, int userId, String searchText) throws ApplicationException {
        int adjustedPage = page - 1;

        searchText = searchText.toLowerCase();

        Pageable pageable = PageRequest.of(adjustedPage, this.purchasesPerPage);
        Page<PurchaseToClient> purchasesPage;

        if(companyIds[0]==null){
            companyIds = companyLogic.getAllCompanyIds();
        }

        if(categoryIds[0]==null){
            categoryIds = categoryLogic.getAllCategoryIds();
        }

        if (userType == UserType.CUSTOMER) {
            purchasesPage = this.purchasesDal.getCustomerPurchasesByFilters(userId, companyIds, categoryIds, searchText, pageable);
        } else {
            purchasesPage = this.purchasesDal.getByFilters(companyIds, categoryIds, searchText, pageable);
        }
        if (purchasesPage == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the purchases you were looking for");
        }
        int totalPages = purchasesPage.getTotalPages();
        List<PurchaseToClient> purchases = purchasesPage.getContent();

        PurchasesPageResult purchasesPageResult = new PurchasesPageResult(purchases, totalPages);

        return purchasesPageResult;
    }

    private void validatePurchase(Purchase purchase) throws ApplicationException {
        validateCouponId(purchase.getCouponId());
        CouponToClient coupon = this.couponLogic.getById(purchase.getCouponId());
        validateAmount(purchase.getAmount(), coupon);
        validatePurchaseDate(purchase.getDate(), coupon);
    }

    private void validateCouponAvailability(int couponId) throws ApplicationException {
        boolean isCouponAvailable = this.couponLogic.isCouponAvailable(couponId);
        if (!isCouponAvailable) {
            throw new ApplicationException(ErrorType.COUPON_NOT_AVAILABLE);
        }
    }

    private void validatePurchaseDate(Date date, CouponToClient coupon) throws ApplicationException {
        Date couponStartDate = coupon.getStartDate();
        Date couponEndDate = coupon.getEndDate();

        if (date.before(couponStartDate)) {
            throw new ApplicationException(ErrorType.PURCHASE_DATE_BEFORE_COUPON_START_DATE);
        }
        if (date.after(couponEndDate)) {
            throw new ApplicationException(ErrorType.PURCHASE_DATE_AFTER_COUPON_END_DATE);
        }
    }

    private void validateAmount(int amount, CouponToClient coupon) throws ApplicationException {
        if (amount < 1 || amount > coupon.getAmount()) {
            throw new ApplicationException(ErrorType.PURCHASE_AMOUNT_NOT_VALID);
        }
    }

    private void validateCouponId(int couponId) throws ApplicationException {
        if (!this.couponLogic.isCouponExist(couponId)) {
            throw new ApplicationException(ErrorType.COUPON_DOES_NOT_EXIST);
        }
    }

    private boolean isPurchaseExist(int id) {
        return this.purchasesDal.existsById(id);
    }

    public void updateAmount(int couponId, int purchasedAmount) throws ApplicationException {
        CouponToClient coupon = this.couponLogic.getById(couponId);
        int newAmount = coupon.getAmount() - purchasedAmount;
        if (newAmount == 0) {
            coupon.setIsAvailable(false);
        } else if (newAmount < 0) {
            throw new ApplicationException(ErrorType.DECREASED_AMOUNT_IS_TOO_LARGE);
        }
        coupon.setAmount(newAmount);
        this.couponLogic.updateCoupon(coupon);
    }
}
