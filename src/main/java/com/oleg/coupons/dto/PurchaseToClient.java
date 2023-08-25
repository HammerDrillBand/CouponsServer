package com.oleg.coupons.dto;

import java.util.Date;

public class PurchaseToClient extends Purchase {
    private String couponName;
    private String couponDescription;
    private int couponCategoryId;
    private String username;
    private String companyName;
    private String contactEmail;

    public PurchaseToClient() {
    }

    public PurchaseToClient(int id, int couponId, int userId, int amount, Date date, String couponName, String couponDescription, int couponCategoryId, String username, String companyName, String contactEmail) {
        super(id, couponId, userId, amount, date);
        this.couponName = couponName;
        this.couponDescription = couponDescription;
        this.couponCategoryId = couponCategoryId;
        this.username = username;
        this.companyName = companyName;
        this.contactEmail = contactEmail;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponDescription() {
        return couponDescription;
    }

    public void setCouponDescription(String couponDescription) {
        this.couponDescription = couponDescription;
    }

    public int getCouponCategoryId() {
        return couponCategoryId;
    }

    public void setCouponCategoryId(int couponCategoryId) {
        this.couponCategoryId = couponCategoryId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString() {
        return "PurchaseToClient{" +
                super.toString() +
                "couponName='" + couponName + '\'' +
                ", couponDescription='" + couponDescription + '\'' +
                ", couponCategory=" + couponCategoryId +
                ", username='" + username + '\'' +
                ", companyName='" + companyName + '\'' +
                ", contactEmail='" + contactEmail + '\'' +
                ", Id=" + Id +
                ", couponId=" + couponId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
