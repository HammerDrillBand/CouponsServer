package com.oleg.coupons.dal;

import com.oleg.coupons.dto.Company;
import com.oleg.coupons.entities.CompanyEntity;
import com.oleg.coupons.enums.CompanyType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICompaniesDal extends CrudRepository<CompanyEntity, Integer> {

    @Query("SELECT new com.oleg.coupons.dto.Company(c.id, c.name, c.companyType, c.registryNumber, c.address, c.contactEmail) FROM CompanyEntity c WHERE c.id = :id")
    Company getById(@Param("id") int id);

    @Query("SELECT new com.oleg.coupons.dto.Company(c.id, c.name, c.companyType, c.registryNumber, c.address, c.contactEmail) FROM CompanyEntity c")
    List<Company> getAll();

    @Query("SELECT new com.oleg.coupons.dto.Company(c.id, c.name, c.companyType, c.registryNumber, c.address, c.contactEmail) FROM CompanyEntity c WHERE c.companyType = :type")
    List<Company> getByType(@Param("type") CompanyType type);

    @Query("SELECT new com.oleg.coupons.dto.Company(c.id, c.name, c.companyType, c.registryNumber, c.address, c.contactEmail) FROM CompanyEntity c WHERE c.registryNumber = :regNum")
    Company getByRegNum(@Param("regNum") int regNum);
}
