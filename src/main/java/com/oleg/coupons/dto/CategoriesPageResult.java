package com.oleg.coupons.dto;

import java.util.List;

public class CategoriesPageResult {
    private List<Category> categories;
    private int totalPages;

    public CategoriesPageResult(List<Category> categories, int totalPages) {
        this.categories = categories;
        this.totalPages = totalPages;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "CategoriesPageResult{" +
                "categories=" + categories +
                ", totalPages=" + totalPages +
                '}';
    }
}