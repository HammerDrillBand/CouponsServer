package com.oleg.coupons.dto;

import java.util.Date;

public class PurchaseToClient extends Purchase {
    private String couponName;
    private String couponDescription;
    private int categoryId;
    private String categoryName;
    private String username;
    private String companyName;
    private int companyId;

    public PurchaseToClient() {
    }

    public PurchaseToClient(int id, int couponId, int userId, int amount, Date date, String couponName, String couponDescription, int CategoryId, String categoryName, String username, int companyId, String companyName) {
        super(id, couponId, userId, amount, date);
        this.couponName = couponName;
        this.couponDescription = couponDescription;
        this.categoryId = CategoryId;
        this.categoryName = categoryName;
        this.username = username;
        this.companyId = companyId;
        this.companyName = companyName;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "PurchaseToClient{" +
                "couponName='" + couponName + '\'' +
                ", couponDescription='" + couponDescription + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", username='" + username + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyId=" + companyId +
                ", Id=" + Id +
                ", couponId=" + couponId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
