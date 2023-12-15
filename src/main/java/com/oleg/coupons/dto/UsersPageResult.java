package com.oleg.coupons.dto;

import java.util.List;

public class UsersPageResult {
    private List<User> users;
    private int totalPages;

    public UsersPageResult(List<User> users, int totalPages) {
        this.users = users;
        this.totalPages = totalPages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "UsersPageResult{" +
                "users=" + users +
                ", totalPages=" + totalPages +
                '}';
    }
}