package com.oleg.coupons.controllers;

import com.oleg.coupons.dto.Coupon;
import com.oleg.coupons.dto.CouponsPageResult;
import com.oleg.coupons.dto.SuccessfulLoginDetails;
import com.oleg.coupons.enums.ErrorType;
import com.oleg.coupons.enums.UserType;
import com.oleg.coupons.logic.CouponLogic;
import com.oleg.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.oleg.coupons.exceptions.ApplicationException;

@RestController
@RequestMapping("/coupons")
public class CouponsController {

    private CouponLogic couponLogic;

    @Autowired
    public CouponsController(CouponLogic couponLogic) {
        this.couponLogic = couponLogic;
    }

    @PostMapping
    public int addCoupon(@RequestHeader("Authorization") String token,
                         @RequestBody Coupon coupon) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType == UserType.CUSTOMER){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        if (userType == UserType.COMPANY) {
            coupon.setCompanyId(successfulLoginDetails.getCompanyId());
        }
        return this.couponLogic.addCoupon(coupon);
    }

    @PutMapping
    public void updateCoupon(@RequestHeader("Authorization") String token,
                             @RequestBody Coupon coupon) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType == UserType.CUSTOMER){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        if (userType == UserType.COMPANY) {
            coupon.setCompanyId(successfulLoginDetails.getCompanyId());
        }
        this.couponLogic.updateCoupon(coupon);
    }

    @DeleteMapping("/{id}")
    public void deleteCoupon(@RequestHeader("Authorization") String token,
                             @PathVariable("id") int id) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType == UserType.CUSTOMER){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        this.couponLogic.deleteCoupon(id);
    }

    @GetMapping("/byFilters")
    public CouponsPageResult getByFilters(@RequestHeader(value = "Authorization", required = false) String token,
                                          @RequestParam("page") int page,
                                          @RequestParam("categoryIds") Integer[] categoryIds,
                                          @RequestParam("companyIds") Integer[] companyIds,
                                          @RequestParam(value = "minPrice", required = false) Float minPrice,
                                          @RequestParam(value = "maxPrice", required = false) Float maxPrice,
                                          @RequestParam(value = "searchText", required = false) String searchText) throws Exception {
        UserType userType;

        if (token == null) {
            userType = UserType.CUSTOMER;
        } else {
            SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
            userType = successfulLoginDetails.getUserType();

            if (userType == UserType.COMPANY) {
                int companyId = successfulLoginDetails.getCompanyId();
                companyIds = new Integer[]{companyId};
            }
        }

        CouponsPageResult coupons = this.couponLogic.getByFilters(userType, page, categoryIds, companyIds, minPrice, maxPrice, searchText);
        return coupons;
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
}
