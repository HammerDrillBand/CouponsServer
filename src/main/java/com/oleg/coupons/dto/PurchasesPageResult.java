package com.oleg.coupons.dto;

import java.util.List;

public class PurchasesPageResult {
    private List<PurchaseToClient> purchases;
    private int totalPages;

    public PurchasesPageResult(List<PurchaseToClient> purchases, int totalPages) {
        this.purchases = purchases;
        this.totalPages = totalPages;
    }

    public List<PurchaseToClient> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseToClient> purchases) {
        this.purchases = purchases;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "PurchasesPageResult{" +
                "purchases=" + purchases +
                ", totalPages=" + totalPages +
                '}';
    }
}