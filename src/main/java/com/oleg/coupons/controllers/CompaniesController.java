package com.oleg.coupons.controllers;

import com.oleg.coupons.dto.CompaniesPageResult;
import com.oleg.coupons.dto.Company;
import com.oleg.coupons.enums.CompanyType;
import com.oleg.coupons.exceptions.ApplicationException;
import com.oleg.coupons.logic.CompanyLogic;
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
    public int addCompany(@RequestBody Company company) throws ApplicationException {
        return this.companyLogic.addCompany(company);
    }

    @PutMapping
    public void updateCompany(@RequestBody Company company) throws ApplicationException {
        this.companyLogic.updateCompany(company);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable("id") int id) throws ApplicationException {
        this.companyLogic.deleteCompany(id);
    }

    @GetMapping
    public List<Company> getAllCompanies() throws ApplicationException {
        return this.companyLogic.getAll();
    }

    @GetMapping("/{id}")
    public Company getCompany(@PathVariable("id") int id) throws ApplicationException {
        return this.companyLogic.getById(id);
    }

    @GetMapping("/byRegNum")
    public Company getByRegNum(@RequestParam("regNum") int regNum) throws ApplicationException {
        return this.companyLogic.getByRegNum(regNum);
    }

    @GetMapping("/byType")
    public List<Company> getByType(@RequestParam("type") CompanyType type) throws ApplicationException {
        return this.companyLogic.getByType(type);
    }

    @GetMapping("/byFilters")
    public CompaniesPageResult getByFilters(@RequestParam("page") int page,
                                            @RequestParam(value = "searchText", required = false) String searchText) throws ApplicationException {
        return this.companyLogic.getByFilters(page, searchText);
    }
}
