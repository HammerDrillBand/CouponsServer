package com.oleg.coupons.entities;


import com.oleg.coupons.dto.Coupon;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "coupons")
public class CouponEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "price", nullable = false)
    private float price;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "is_available", nullable = false)
    private boolean isAvailable;

    @ManyToOne(fetch = FetchType.EAGER)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.EAGER)
    private CompanyEntity company;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<PurchaseEntity> purchasesList;

    public CouponEntity() {
    }

    public CouponEntity(Coupon coupon) {
        this.id = coupon.getId();
        this.name = coupon.getName();
        this.description = coupon.getDescription();
        this.startDate = coupon.getStartDate();
        this.endDate = coupon.getEndDate();
        this.price = coupon.getPrice();
        this.amount = coupon.getAmount();
        this.isAvailable = true;
        this.category = new CategoryEntity();
        int categoryId = coupon.getCategoryId();
        this.category.setId(categoryId);
        this.company = new CompanyEntity();
        int companyId = coupon.getCompanyId();
        this.company.setId(companyId);
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public List<PurchaseEntity> getPurchasesList() {
        return purchasesList;
    }

    public void setPurchasesList(List<PurchaseEntity> purchasesList) {
        this.purchasesList = purchasesList;
    }
}
