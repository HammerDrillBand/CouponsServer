package com.oleg.coupons.controllers;

import com.oleg.coupons.dto.CategoriesPageResult;
import com.oleg.coupons.dto.Category;
import com.oleg.coupons.dto.SuccessfulLoginDetails;
import com.oleg.coupons.enums.ErrorType;
import com.oleg.coupons.enums.UserType;
import com.oleg.coupons.exceptions.ApplicationException;
import com.oleg.coupons.logic.CategoryLogic;
import com.oleg.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private CategoryLogic categoryLogic;

    @Autowired
    public CategoriesController(CategoryLogic categoryLogic) {
        this.categoryLogic = categoryLogic;
    }

    @PostMapping
    public int addCategory(@RequestHeader(value = "Authorization") String token,
                           @RequestBody Category category) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType != UserType.ADMIN){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        return this.categoryLogic.addCategory(category);
    }

    @PutMapping
    public void updateCategory(@RequestHeader(value = "Authorization") String token,
                               @RequestBody Category category) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType != UserType.ADMIN){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        this.categoryLogic.updateCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@RequestHeader(value = "Authorization") String token,
                               @PathVariable("id") int id) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType != UserType.ADMIN){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        this.categoryLogic.deleteCategory(id);
    }

    @GetMapping
    public List<Category> getAllCategories() throws ApplicationException {
        return this.categoryLogic.getAll();
    }

    @GetMapping("/byFilters")
    public CategoriesPageResult getByFilters(@RequestHeader(value = "Authorization") String token,
                                             @RequestParam("page") int page,
                                             @RequestParam(value = "searchText", required = false) String searchText) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType != UserType.ADMIN){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        return this.categoryLogic.getByFilters(page, searchText);
    }
}
