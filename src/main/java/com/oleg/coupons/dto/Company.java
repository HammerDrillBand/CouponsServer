package com.oleg.coupons.dto;

import com.oleg.coupons.enums.CompanyType;

public class Company {
    private int id;
    private String name;
    private CompanyType companyType;
    private int registryNumber;
    private String address;
    private String contactEmail;

    public Company() {
    }

    public Company(String name, CompanyType companyType, int registryNumber, String address, String contactEmail) {
        this.name = name;
        this.companyType = companyType;
        this.registryNumber = registryNumber;
        this.address = address;
        this.contactEmail = contactEmail;
    }

    public Company(int id, String name, CompanyType companyType, int registryNumber, String address, String contactEmail) {
        this(name, companyType, registryNumber, address, contactEmail);
        this.id = id;
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

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + id +
                ", name='" + name + '\'' +
                ", registryNumber=" + registryNumber +
                ", companyType=" + companyType +
                ", address='" + address + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                '}';
    }
}
