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

    @Autowired
    public PurchaseLogic(IPurchasesDal purchasesDal, CouponLogic couponLogic) {
        this.purchasesDal = purchasesDal;
        this.couponLogic = couponLogic;
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

    @Transactional(rollbackFor = ApplicationException.class)
    public void updatePurchase(Purchase purchase) throws ApplicationException {
        validatePurchase(purchase);
        Purchase previousRevPurchase = getById(purchase.getId());

        int oldAmount = previousRevPurchase.getAmount();
        int newAmount = purchase.getAmount();
        int varianceInAmount = oldAmount - newAmount;
        if (varianceInAmount != 0) {
            updateAmount(purchase.getCouponId(), purchase.getAmount());
        }

        PurchaseEntity purchaseEntity = new PurchaseEntity(purchase);
        try {
            this.purchasesDal.save(purchaseEntity);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to update purchase", e);
        }
    }

    public void deletePurchase(int id) throws ApplicationException {
        if (!isPurchaseExist(id)) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the purchase to be deleted");
        }
        try {
            this.purchasesDal.deleteById(id);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to delete purchase with id " + id, e);
        }
    }

    public PurchaseToClient getById(int id) throws ApplicationException {
        PurchaseToClient purchase = this.purchasesDal.getById(id);
        if (purchase == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the purchase you were looking for");
        }
        return purchase;
    }

    public List<PurchaseToClient> getAll() throws ApplicationException {
        List<PurchaseToClient> purchases = this.purchasesDal.getAll();
        if (purchases == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the purchases you were looking for");
        }
        return purchases;
    }

    public PurchasesPageResult getByFilters(int page, int[] companyIds, int[] categoryIds, UserType userType, int userId) throws ApplicationException {
        int adjustedPage = page - 1;
        Pageable pageable = PageRequest.of(adjustedPage, this.purchasesPerPage);
        Page<PurchaseToClient> purchasesPage;
        if (userType == UserType.CUSTOMER) {
            purchasesPage = this.purchasesDal.getCustomerPurchasesByFilters(userId, companyIds, categoryIds, pageable);
        } else {
            purchasesPage = this.purchasesDal.getByFilters(companyIds, categoryIds, pageable);
        }
        if (purchasesPage == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the purchases you were looking for");
        }
        int totalPages = purchasesPage.getTotalPages();
        List<PurchaseToClient> purchases = purchasesPage.getContent();

        PurchasesPageResult purchasesPageResult = new PurchasesPageResult(purchases, totalPages);

        return purchasesPageResult;
    }

    public PurchasesPageResult getByCompanyId(int companyId, int page) throws ApplicationException {
        int adjustedPage = page - 1;
        Pageable pageable = PageRequest.of(adjustedPage, this.purchasesPerPage);

        Page<PurchaseToClient> purchasesPage = this.purchasesDal.getByCompanyId(companyId, pageable);
        if (purchasesPage == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the purchases you were looking for");
        }

        int totalPages = purchasesPage.getTotalPages();
        List<PurchaseToClient> purchases = purchasesPage.getContent();

        PurchasesPageResult purchasesPageResult = new PurchasesPageResult(purchases, totalPages);

        return purchasesPageResult;
    }

    public PurchasesPageResult getByUserId(int userId, int page) throws ApplicationException {
        int adjustedPage = page - 1;
        Pageable pageable = PageRequest.of(adjustedPage, this.purchasesPerPage);

        Page<PurchaseToClient> purchasesPage = this.purchasesDal.getByUserId(userId, pageable);
        if (purchasesPage == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the purchases you were looking for");
        }

        int totalPages = purchasesPage.getTotalPages();
        List<PurchaseToClient> purchases = purchasesPage.getContent();

        PurchasesPageResult purchasesPageResult = new PurchasesPageResult(purchases, totalPages);

        return purchasesPageResult;
    }

    public List<PurchaseToClient> getByCategory(int categoryId) throws ApplicationException {
        List<PurchaseToClient> purchases = this.purchasesDal.getByCategory(categoryId);
        if (purchases == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the purchases you were looking for");
        }
        return purchases;
    }

    public List<PurchaseToClient> getByCategory(String categoryName) throws ApplicationException {
        List<PurchaseToClient> purchases = this.purchasesDal.getByCategory(categoryName);
        if (purchases == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the purchases you were looking for");
        }
        return purchases;
    }

    public List<PurchaseToClient> getByDateRange(Date fromDate, Date toDate) throws ApplicationException {
        List<PurchaseToClient> purchases = this.purchasesDal.getByDateRange(fromDate, toDate);
        if (purchases == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the purchases you were looking for");
        }
        return purchases;
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
