package com.oleg.coupons.controllers;

import com.oleg.coupons.dto.Coupon;
import com.oleg.coupons.dto.CouponToClient;
import com.oleg.coupons.dto.CouponsPageResult;
import com.oleg.coupons.dto.SuccessfulLoginDetails;
import com.oleg.coupons.enums.UserType;
import com.oleg.coupons.exceptions.ApplicationException;
import com.oleg.coupons.logic.CouponLogic;
import com.oleg.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponsController {

    private CouponLogic couponLogic;

    @Autowired
    public CouponsController(CouponLogic couponLogic) {
        this.couponLogic = couponLogic;
    }

    @PostMapping
    public int addCoupon(@RequestHeader("Authorization") String token, @RequestBody Coupon coupon) throws Exception {
        Coupon adjustedCoupon = adjustCompanyIdByUserType(token, coupon);
        return this.couponLogic.addCoupon(adjustedCoupon);
    }

    @PutMapping
    public void updateCoupon(@RequestHeader("Authorization") String token, @RequestBody Coupon coupon) throws Exception {
        Coupon adjustedCoupon = adjustCompanyIdByUserType(token, coupon);
        this.couponLogic.updateCoupon(adjustedCoupon);
    }

    @DeleteMapping("/{id}")
    public void deleteCoupon(@PathVariable("id") int id) throws ApplicationException {
        this.couponLogic.deleteCoupon(id);
    }

    @GetMapping
    public List<CouponToClient> getAllCoupons() throws ApplicationException {
        return this.couponLogic.getAll();
    }

    @GetMapping("/available")
    public List<CouponToClient> getAllAvailable() throws ApplicationException {
        return this.couponLogic.getAllAvailable();
    }

    @GetMapping("/{id}")
    public CouponToClient getCoupon(@PathVariable("id") int id) throws ApplicationException {
        return this.couponLogic.getById(id);
    }

    @GetMapping("/byFilters")
    public CouponsPageResult getByFilters(@RequestHeader(value = "Authorization", required = false) String token, @RequestParam("page") int page, @RequestParam("categoryIds") int[] categoryIds, @RequestParam("companyIds") int[] companyIds, @RequestParam(value = "minPrice", required = false) Float minPrice, @RequestParam(value = "maxPrice", required = false) Float maxPrice) throws Exception {
        UserType userType;

        if (token == null) {
            userType = UserType.CUSTOMER;
        } else {
            SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
            userType = successfulLoginDetails.getUserType();

            if (userType == UserType.COMPANY) {
                int companyId = successfulLoginDetails.getCompanyId();
                companyIds = new int[]{companyId};
            }
        }

        CouponsPageResult coupons = this.couponLogic.getByFilters(userType, page, categoryIds, companyIds, minPrice, maxPrice);
        return coupons;
    }

    @GetMapping("/belowPrice")
    public List<CouponToClient> getCouponsBelowPrice(@RequestParam("price") float maxPrice) throws ApplicationException {
        return this.couponLogic.getBelowPrice(maxPrice);
    }

    @GetMapping("/byCompanyId")
    public List<CouponToClient> getCouponsByCompanyId(@RequestParam("companyId") int companyId) throws ApplicationException {
        return this.couponLogic.getByCompanyId(companyId);
    }

    @GetMapping("/byCategoryId")
    public List<CouponToClient> getCouponsByCategory(@RequestParam("categoryId") int categoryId) throws ApplicationException {
        return this.couponLogic.getByCategoryId(categoryId);
    }


    @GetMapping("/byCategoryName")
    public List<CouponToClient> getCouponsByCategory(@RequestParam("categoryName") String categoryName) throws ApplicationException {
        return this.couponLogic.getByCategoryName(categoryName);
    }

    @GetMapping("/minPrice")
    public Float getMinPrice(@RequestHeader(value = "Authorization", required = false) String token) throws Exception {
        if (token == null) {
            return this.couponLogic.getMinPrice();
        }
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType == UserType.COMPANY) {
            int companyId = successfulLoginDetails.getCompanyId();
            return this.couponLogic.getMinPriceByCompany(companyId);
        }
        return this.couponLogic.getMinPrice();
    }

    @GetMapping("/maxPrice")
    public Float getMaxPrice(@RequestHeader(value = "Authorization", required = false) String token) throws Exception {
        if (token == null) {
            return this.couponLogic.getMaxPrice();
        }
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType == UserType.COMPANY) {
            int companyId = successfulLoginDetails.getCompanyId();
            return this.couponLogic.getMaxPriceByCompany(companyId);
        }
        return this.couponLogic.getMaxPrice();
    }


    private Coupon adjustCompanyIdByUserType(String token, Coupon coupon) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType == UserType.COMPANY) {
            coupon.setCompanyId(successfulLoginDetails.getCompanyId());
        }
        return coupon;
    }
}
