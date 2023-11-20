package com.oleg.coupons.controllers;

import com.oleg.coupons.dto.Coupon;
import com.oleg.coupons.dto.CouponToClient;
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
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        coupon.setCompanyId(successfulLoginDetails.getCompanyId());
        return this.couponLogic.addCoupon(coupon);
    }

    @PutMapping
    public void updateCoupon(@RequestHeader("Authorization") String token, @RequestBody Coupon coupon) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType == UserType.COMPANY) {
            coupon.setCompanyId(successfulLoginDetails.getCompanyId());
        }
        this.couponLogic.updateCoupon(coupon);
    }

    @DeleteMapping("/{id}")
    public void deleteCoupon(@PathVariable("id") int id) throws ApplicationException {
        this.couponLogic.deleteCoupon(id);
    }

    @GetMapping
    public List<CouponToClient> getAllCoupons() throws ApplicationException {
        return this.couponLogic.getAll();
    }

    @GetMapping("/{id}")
    public CouponToClient getCoupon(@PathVariable("id") int id) throws ApplicationException {
        return this.couponLogic.getById(id);
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

}
