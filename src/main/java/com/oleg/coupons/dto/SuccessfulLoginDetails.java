package com.oleg.coupons.dto;

import com.oleg.coupons.enums.UserType;

public class SuccessfulLoginDetails {
    private int id;
    private UserType userType;
    private Integer companyId;

    public SuccessfulLoginDetails(int id, UserType userType, Integer companyId) {
        this.id = id;
        this.userType = userType;
        this.companyId = companyId;
    }

    public SuccessfulLoginDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "SuccessfulLoginDetails{" +
                "id=" + id +
                ", userType=" + userType +
                ", companyId=" + companyId +
                '}';
    }
}
