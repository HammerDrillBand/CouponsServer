package com.oleg.coupons.dal;

import com.oleg.coupons.dto.Company;
import com.oleg.coupons.entities.CompanyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICompaniesDal extends CrudRepository<CompanyEntity, Integer> {

    @Query("SELECT new com.oleg.coupons.dto.Company(c.id, c.name, c.companyType, c.registryNumber, c.address, c.contactEmail) FROM CompanyEntity c WHERE c.id = :id")
    Company getById(@Param("id") int id);

    @Query("SELECT new com.oleg.coupons.dto.Company(c.id, c.name, c.companyType, c.registryNumber, c.address, c.contactEmail) FROM CompanyEntity c")
    List<Company> getAll();

    @Query("SELECT new com.oleg.coupons.dto.Company(c.id, c.name, c.companyType, c.registryNumber, c.address, c.contactEmail) FROM CompanyEntity c WHERE (LOWER(c.name) LIKE %:searchText%)")
    Page<Company> getByFilters(
            @Param("searchText") String searchText,
            Pageable pageable);

    @Query("SELECT (c.id) FROM CompanyEntity c")
    Integer[] getAllCompanyIds();
}
