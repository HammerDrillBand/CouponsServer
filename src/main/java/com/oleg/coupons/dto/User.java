package com.oleg.coupons.dto;

import com.oleg.coupons.enums.UserType;

public class User {
    private int id;
    private String username;
    private String password;
    private UserType userType;
    private Integer companyId;

    public User() {
    }

    public User(String username, String password, UserType userType, Integer companyId) {
        this(username, password, userType);
        if (userType.getUserTypeName().equals("Company")) {
            this.companyId = companyId;
        }
    }

    public User(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.companyId = null;
    }

    public User(int id, String username, String password, UserType userType, Integer companyId) {
        this(username, password, userType, companyId);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", companyId=" + companyId +
                '}';
    }
}
