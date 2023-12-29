package com.oleg.coupons.controllers;

import com.oleg.coupons.dto.SuccessfulLoginDetails;
import com.oleg.coupons.dto.UserLoginData;
import com.oleg.coupons.dto.UsersPageResult;
import com.oleg.coupons.enums.ErrorType;
import com.oleg.coupons.enums.UserType;
import com.oleg.coupons.logic.UserLogic;
import com.oleg.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.oleg.coupons.exceptions.ApplicationException;
import com.oleg.coupons.dto.User;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UserLogic userLogic;

    @Autowired
    public UsersController(UserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @PostMapping
    public int addUser(@RequestBody User user) throws ApplicationException {
        return this.userLogic.addUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginData loginDetails) throws ApplicationException {
        return this.userLogic.login(loginDetails);
    }

    @PutMapping
    public void updateUser(@RequestHeader("Authorization") String token,
                           @RequestBody User user) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType != UserType.ADMIN){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        this.userLogic.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@RequestHeader("Authorization") String token,
                           @PathVariable("id") int id) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType != UserType.ADMIN){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        this.userLogic.deleteUser(id);
    }

    @GetMapping("/byFilters")
    public UsersPageResult getByFilters(@RequestHeader("Authorization") String token,
                                        @RequestParam("page") int page,
                                        @RequestParam(value = "companyIds", required = false) Integer[] companyIds,
                                        @RequestParam(value = "searchText", required = false) String searchText) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType != UserType.ADMIN){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        return this.userLogic.getByFilters(page, companyIds, searchText);
    }
}
