package com.oleg.coupons.enums;

public enum UserType {
    CUSTOMER("Customer"),
    ADMIN("Admin"),
    COMPANY("Company");

    private String userTypeName;

    UserType(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getUserTypeName() {
        return userTypeName;
    }
}
