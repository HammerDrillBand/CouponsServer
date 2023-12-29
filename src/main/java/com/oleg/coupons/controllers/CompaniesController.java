package com.oleg.coupons.controllers;

import com.oleg.coupons.dto.CompaniesPageResult;
import com.oleg.coupons.dto.Company;
import com.oleg.coupons.dto.SuccessfulLoginDetails;
import com.oleg.coupons.enums.ErrorType;
import com.oleg.coupons.enums.UserType;
import com.oleg.coupons.exceptions.ApplicationException;
import com.oleg.coupons.logic.CompanyLogic;
import com.oleg.coupons.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    private CompanyLogic companyLogic;

    @Autowired
    public CompaniesController(CompanyLogic companyLogic) {
        this.companyLogic = companyLogic;
    }

    @PostMapping
    public int addCompany(@RequestHeader(value = "Authorization") String token,
                          @RequestBody Company company) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType != UserType.ADMIN){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        return this.companyLogic.addCompany(company);
    }

    @PutMapping
    public void updateCompany(@RequestHeader(value = "Authorization") String token,
                              @RequestBody Company company) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType != UserType.ADMIN){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        this.companyLogic.updateCompany(company);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@RequestHeader(value = "Authorization") String token,
                              @PathVariable("id") int id) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType != UserType.ADMIN){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        this.companyLogic.deleteCompany(id);
    }

    @GetMapping
    public List<Company> getAllCompanies() throws ApplicationException {
        return this.companyLogic.getAll();
    }

    @GetMapping("/{id}")
    public Company getCompany(@RequestHeader(value = "Authorization") String token,
                              @PathVariable("id") int id) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType != UserType.COMPANY){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        return this.companyLogic.getById(id);
    }

    @GetMapping("/byFilters")
    public CompaniesPageResult getByFilters(@RequestHeader(value = "Authorization") String token,
                                            @RequestParam("page") int page,
                                            @RequestParam(value = "searchText", required = false) String searchText) throws Exception {
        SuccessfulLoginDetails successfulLoginDetails = JWTUtils.decodeJWT(token);
        UserType userType = successfulLoginDetails.getUserType();
        if (userType != UserType.ADMIN){
            throw new ApplicationException(ErrorType.UNAUTHORIZED_REQUEST);
        }
        return this.companyLogic.getByFilters(page, searchText);
    }
}
