package com.oleg.coupons.dto;

import java.util.List;

public class CouponsPageResult {
    private List<CouponToClient> coupons;
    private int totalPages;

    public CouponsPageResult(List<CouponToClient> coupons, int totalPages) {
        this.coupons = coupons;
        this.totalPages = totalPages;
    }

    public List<CouponToClient> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponToClient> coupons) {
        this.coupons = coupons;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "CouponsPageResult{" +
                "coupons=" + coupons +
                ", totalPages=" + totalPages +
                '}';
    }
}