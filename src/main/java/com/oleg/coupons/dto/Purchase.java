package com.oleg.coupons.dto;

import java.util.Date;

public class Purchase {
    int Id;
    int couponId;
    int userId;
    int amount;
    Date date;

    public Purchase() {
    }

    public Purchase(int couponId, int userId, int amount, Date date) {
        this.couponId = couponId;
        this.userId = userId;
        this.amount = amount;
        this.date = date;
    }

    public Purchase(int id, int couponId, int userId, int amount, Date date) {
        this(couponId, userId, amount, date);
        this.Id = id;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    @Override
    public String toString() {
        return "Purchase{" +
                "Id=" + Id +
                ", couponId=" + couponId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
