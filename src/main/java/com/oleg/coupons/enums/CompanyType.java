package com.oleg.coupons.enums;

public enum CompanyType {
    PRIVATE_OWNERSHIP("Private ownership"),
    PARTNERSHIP("Partnership"),
    CORPORATION("Corporation"),
    LLC("Limited (ltd) Liability Company");

    private String companyTypeName;

    CompanyType(String companyTypeName) {
        this.companyTypeName = companyTypeName;
    }

    public String getCompanyTypeName() {
        return companyTypeName;
    }
}
