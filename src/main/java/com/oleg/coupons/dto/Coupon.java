package com.oleg.coupons.dto;

import java.util.Date;

public class Coupon {
    private int id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private float price;
    private int amount;
    private int categoryId;
    private int companyId;
    private boolean isAvailable;
    private byte[] imageData;
//    private Blob imageBlob;


    public Coupon() {
    }

    public Coupon(String name, String description, Date startDate, Date endDate, float price, int amount, int categoryId, int companyId, byte[] imageData) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.amount = amount;
        this.categoryId = categoryId;
        this.companyId = companyId;
        this.isAvailable = true;
        this.imageData = imageData;
    }

    public Coupon(int id, String name, String description, Date startDate, Date endDate, float price, int amount, int categoryId, int companyId, byte[] imageData) {
        this(name, description, startDate, endDate, price, amount, categoryId, companyId, imageData);
        this.id = id;
    }

//    public Coupon(String name, String description, Date startDate, Date endDate, float price, int amount, int categoryId, int companyId, Blob imageBlob) {
//        this.name = name;
//        this.description = description;
//        this.startDate = startDate;
//        this.endDate = endDate;
//        this.price = price;
//        this.amount = amount;
//        this.categoryId = categoryId;
//        this.companyId = companyId;
//        this.isAvailable = true;
//        this.imageBlob = imageBlob;
//    }
//
//    public Coupon(int id, String name, String description, Date startDate, Date endDate, float price,int amount, int categoryId, int companyId, Blob imageBlob) {
//        this(name, description, startDate, endDate, price, amount, categoryId, companyId, imageBlob);
//        this.id = id;
//    }

    public Coupon(CouponToClient couponToClient) {
        this.id = couponToClient.getId();
        this.name = couponToClient.getName();
        this.description = couponToClient.getDescription();
        this.startDate = couponToClient.getStartDate();
        this.endDate = couponToClient.getEndDate();
        this.price = couponToClient.getPrice();
        this.amount = couponToClient.getAmount();
        this.categoryId = couponToClient.getCategoryId();
        this.companyId = couponToClient.getCompanyId();
        this.isAvailable = couponToClient.isAvailable();
//        this.imageBlob = couponToClient.getImageBlob();
        this.imageData = couponToClient.getImageData();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] image) {
        this.imageData = image;
    }

//    public Blob getImageBlob() {
//        return imageBlob;
//    }
//
//    public void setImageBlob(Blob imageBlob) {
//        this.imageBlob = imageBlob;
//    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", amount=" + amount +
                ", categoryId=" + categoryId +
                ", companyId=" + companyId +
                ", isAvailable=" + isAvailable +
                '}';
    }
}
