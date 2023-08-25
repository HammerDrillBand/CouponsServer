package com.oleg.coupons.entities;

import com.oleg.coupons.dto.Company;
import com.oleg.coupons.enums.CompanyType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "companies")
public class CompanyEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "company_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;

    @Column(name = "registry_number", nullable = false, unique = true)
    private int registryNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "contact_email", nullable = false)
    private String contactEmail;

    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<UserEntity> employeeList;

    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<CouponEntity> couponsList;

    public CompanyEntity() {
    }

    public CompanyEntity(Company company) {
        this.id = company.getId();
        this.name = company.getName();
        this.companyType = company.getCompanyType();
        this.registryNumber = company.getRegistryNumber();
        this.address = company.getAddress();
        this.contactEmail = company.getContactEmail();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public int getRegistryNumber() {
        return registryNumber;
    }

    public void setRegistryNumber(int registryNumber) {
        this.registryNumber = registryNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public List<UserEntity> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<UserEntity> employeeList) {
        this.employeeList = employeeList;
    }

    public List<CouponEntity> getCouponsList() {
        return couponsList;
    }

    public void setCouponsList(List<CouponEntity> couponsList) {
        this.couponsList = couponsList;
    }
}
