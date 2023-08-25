package com.oleg.coupons.entities;

import com.oleg.coupons.dto.Purchase;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "purchases")
public class PurchaseEntity {

    @Id
    @GeneratedValue
    int id;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    private CouponEntity coupon;

    public PurchaseEntity() {
    }

    public PurchaseEntity(Purchase purchase) {
        this.id = purchase.getId();
        this.amount = purchase.getAmount();
        this.date = purchase.getDate();
        this.user = new UserEntity();
        int purchaseId = purchase.getUserId();
        this.user.setId(purchaseId);
        this.coupon = new CouponEntity();
        int couponId = purchase.getCouponId();
        this.coupon.setId(couponId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public CouponEntity getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponEntity coupon) {
        this.coupon = coupon;
    }
}
