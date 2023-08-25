package com.oleg.coupons.entities;

import com.oleg.coupons.dto.User;
import com.oleg.coupons.enums.UserType;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @ManyToOne (fetch = FetchType.EAGER)
    private CompanyEntity company;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<PurchaseEntity> purchasesList;

    public UserEntity() {
    }

    public UserEntity(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.userType = user.getUserType();
        if (userType==UserType.COMPANY){
            this.company = new CompanyEntity();
            int companyId = user.getCompanyId();
            this.company.setId(companyId);
        }
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

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public List<PurchaseEntity> getPurchasesList() {
        return purchasesList;
    }

    public void setPurchasesList(List<PurchaseEntity> purchasesList) {
        this.purchasesList = purchasesList;
    }

}
