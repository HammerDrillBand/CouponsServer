package com.oleg.coupons.dto;


import java.util.Date;

public class CouponToClient extends Coupon {
    private String categoryName;
    private String companyName;

    public CouponToClient() {
    }

    public CouponToClient(int id, String name, String description, Date startDate, Date endDate, int amount, float price, int categoryId, String categoryName, int companyId, String companyName, byte[] imageData) {
        super(id, name, description, startDate, endDate, price, amount, categoryId, companyId, imageData);
        this.categoryName = categoryName;
        this.companyName = companyName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return "CouponToClient{" +
                super.toString() +
                "categoryName='" + categoryName + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
