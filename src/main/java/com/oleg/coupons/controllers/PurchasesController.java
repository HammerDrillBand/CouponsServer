package com.oleg.coupons.controllers;

import com.oleg.coupons.dto.Purchase;
import com.oleg.coupons.dto.PurchasesPageResult;
import com.oleg.coupons.dto.SuccessfulLoginDetails;
import com.oleg.coupons.enums.ErrorType;
import com.oleg.coupons.enums.UserType;
import com.oleg.coupons.exceptions.ApplicationException;
import com.oleg.coupons.logic.PurchaseLogic;
import com.oleg.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/purchases")
public class PurchasesController {

    private PurchaseLogic purchaseLogic;

    @Autowired
    public PurchasesController(PurchaseLogic purchaseLogic) {
        this.purchaseLogic = purchaseLogic;
    }

    @PostMapping
    public int addPurchase(@RequestHeader("Authorization") String token, @RequestBody Purchase purchase) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType != UserType.CUSTOMER){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        purchase.setUserId(successfulLoginDetails.getId());
        purchase.setDate(new Date());
        return this.purchaseLogic.addPurchase(purchase);
    }

    @GetMapping("/byFilters")
    public PurchasesPageResult getByFilters(@RequestHeader(value = "Authorization", required = false) String token,
                                            @RequestParam("page") int page,
                                            @RequestParam("companyIds") Integer[] companyIds,
                                            @RequestParam("categoryIds") Integer[] categoryIds,
                                            @RequestParam(value = "searchText", required = false) String searchText) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        int userId = successfulLoginDetails.getId();

        if (userType == UserType.COMPANY) {
            int companyId = successfulLoginDetails.getCompanyId();
            companyIds = new Integer[]{companyId};
        }

        return this.purchaseLogic.getByFilters(page, companyIds, categoryIds, userType, userId, searchText);
    }
}
