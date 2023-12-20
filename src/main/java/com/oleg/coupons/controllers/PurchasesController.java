package com.oleg.coupons.controllers;

import com.oleg.coupons.dto.Purchase;
import com.oleg.coupons.dto.PurchaseToClient;
import com.oleg.coupons.dto.PurchasesPageResult;
import com.oleg.coupons.dto.SuccessfulLoginDetails;
import com.oleg.coupons.enums.UserType;
import com.oleg.coupons.exceptions.ApplicationException;
import com.oleg.coupons.logic.PurchaseLogic;
import com.oleg.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
        purchase.setUserId(successfulLoginDetails.getId());
        purchase.setDate(new Date());
        return this.purchaseLogic.addPurchase(purchase);
    }

    @PutMapping
    public void updatePurchase(@RequestBody Purchase purchase) throws ApplicationException {
        this.purchaseLogic.updatePurchase(purchase);
    }

    @DeleteMapping("/{id}}")
    public void deletePurchase(@PathVariable("id") int id) throws ApplicationException {
        this.purchaseLogic.deletePurchase(id);
    }

    @GetMapping
    public List<PurchaseToClient> getAllPurchases() throws ApplicationException {
        return this.purchaseLogic.getAll();
    }

    @GetMapping("/{id}")
    public PurchaseToClient getPurchase(@PathVariable("id") int id) throws ApplicationException {
        return this.purchaseLogic.getById(id);
    }

    @GetMapping("/byCompanyId")
    public PurchasesPageResult getPurchasesByCompanyId(@RequestHeader("Authorization") String token, @RequestParam("page") int page) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        return this.purchaseLogic.getByCompanyId(successfulLoginDetails.getCompanyId(), page);
    }

    //For admin use
//    @GetMapping("/byUserId")
//    public List<PurchaseToClient> getPurchasesByUserId(@RequestParam("userId") int userId) throws ApplicationException {
//        return this.purchaseLogic.getByUserId(userId);
//    }

    //For user use
    @GetMapping("/byUserId")
    public PurchasesPageResult getPurchasesByUser(@RequestHeader("Authorization") String token, @RequestParam("page") int page) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        return this.purchaseLogic.getByUserId(successfulLoginDetails.getId(), page);
    }

    @GetMapping("/byCategoryId")
    public List<PurchaseToClient> getPurchasesByCategoryId(@RequestParam("categoryId") int categoryId) throws ApplicationException {
        return this.purchaseLogic.getByCategory(categoryId);
    }

    @GetMapping("/byCategoryName")
    public List<PurchaseToClient> getPurchasesByCategoryName(@RequestParam("categoryName") String categoryName) throws ApplicationException {
        return this.purchaseLogic.getByCategory(categoryName);
    }

    @GetMapping("/byDateRange")
    public List<PurchaseToClient> getPurchasesByDateRange(@RequestParam("fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, @RequestParam("toDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) throws ApplicationException {
        return this.purchaseLogic.getByDateRange(fromDate, toDate);
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
