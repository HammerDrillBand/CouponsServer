package com.oleg.coupons.logic;

import com.oleg.coupons.dal.ICompaniesDal;
import com.oleg.coupons.dto.Company;
import com.oleg.coupons.entities.CompanyEntity;
import com.oleg.coupons.enums.CompanyType;
import com.oleg.coupons.enums.ErrorType;
import com.oleg.coupons.exceptions.ApplicationException;
import com.oleg.coupons.utils.CommonValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyLogic {
    private ICompaniesDal companiesDal;

    @Autowired
    public CompanyLogic(ICompaniesDal companiesDal) {
        this.companiesDal = companiesDal;
    }

    public int addCompany(Company company) throws ApplicationException {
        validateCompany(company);
        CompanyEntity companyEntity = new CompanyEntity(company);
        try {
            companyEntity = this.companiesDal.save(companyEntity);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to add company", e);
        }
        return companyEntity.getId();
    }

    public void updateCompany(Company company) throws ApplicationException {
        validateCompany(company);

        CompanyEntity companyEntity = new CompanyEntity(company);
        try {
            this.companiesDal.save(companyEntity);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to update company", e);
        }
    }

    public void deleteCompany(int id) throws ApplicationException {
        if (!isCompanyExist(id)) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the company to be deleted");
        }
        try {
            this.companiesDal.deleteById(id);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to delete company with id " + id, e);
        }
    }

    public Company getById(int id) throws ApplicationException {
        Company company = this.companiesDal.getById(id);
        if (company == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the company you were looking for");
        }
        return company;
    }

    public Company getByRegNum(int regNum) throws ApplicationException {
        Company company = this.companiesDal.getByRegNum(regNum);
        if (company == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the company you were looking for");
        }
        return company;
    }

    public List<Company> getAll() throws ApplicationException {
        List<Company> companies = this.companiesDal.getAll();
        if (companies == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the companies you were looking for");
        }
        return companies;
    }

    public List<Company> getByType(CompanyType type) throws ApplicationException {
        List<Company> companies = this.companiesDal.getByType(type);
        if (companies == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the companies you were looking for");
        }
        return companies;
    }

    private void validateCompany(Company company) throws ApplicationException {
        validateCompanyName(company.getName());
        validateCompanyType(company.getCompanyType());
        validateCompanyRegistryNumber(company.getRegistryNumber());
        validateCompanyAddress(company.getAddress());
        ValidateCompanyContactEmail(company.getContactEmail());
    }

    private void ValidateCompanyContactEmail(String email) throws ApplicationException {
        if (!CommonValidations.validateEmailAddressStructure(email)) {
            throw new ApplicationException(ErrorType.COMPANY_EMAIL_INVALID);
        }
    }

    private void validateCompanyAddress(String address) throws ApplicationException {
        int validationResult = CommonValidations.validateStringLength(address, 5, 45);
        if (validationResult == 1) {
            throw new ApplicationException(ErrorType.COMPANY_ADDRESS_TOO_LONG);
        }
        if (validationResult == -1) {
            throw new ApplicationException(ErrorType.COMPANY_ADDRESS_TOO_SHORT);
        }
    }

    private void validateCompanyName(String name) throws ApplicationException {
        int validationResult = CommonValidations.validateStringLength(name, 2, 45);
        if (validationResult == 1) {
            throw new ApplicationException(ErrorType.COMPANY_NAME_TOO_LONG);
        }
        if (validationResult == -1) {
            throw new ApplicationException(ErrorType.COMPANY_NAME_TOO_SHORT);
        }
    }

    private void validateCompanyType(CompanyType type) throws ApplicationException {
        if (type == null) {
            throw new ApplicationException(ErrorType.COMPANY_TYPE_NULL);
        }
    }

    private void validateCompanyRegistryNumber(int regNum) throws ApplicationException {
        if (regNum > 520000000 || regNum < 509999999) {
            throw new ApplicationException(ErrorType.COMPANY_REGISTRY_NUMBER_INVALID);
        }
    }

    public boolean isCompanyExist(Integer id) {
        return this.companiesDal.existsById(id);
    }

}
