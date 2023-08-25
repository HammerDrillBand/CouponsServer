package com.oleg.coupons.entities;

import com.oleg.coupons.dto.Category;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<CouponEntity> couponsList;

    public CategoryEntity() {
    }

    public CategoryEntity(Category category) {
        this.id = category.getId();
        this.name = category.getName();
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

    public List<CouponEntity> getCouponsList() {
        return couponsList;
    }

    public void setCouponsList(List<CouponEntity> couponsList) {
        this.couponsList = couponsList;
    }
}
