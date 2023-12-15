package com.oleg.coupons.dto;

import java.util.List;

public class CompaniesPageResult {
    private List<Company> companies;
    private int totalPages;

    public CompaniesPageResult(List<Company> companies, int totalPages) {
        this.companies = companies;
        this.totalPages = totalPages;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "CompaniesPageResult{" +
                "companies=" + companies +
                ", totalPages=" + totalPages +
                '}';
    }
}