package com.oleg.coupons.dal;

import com.oleg.coupons.dto.SuccessfulLoginDetails;
import com.oleg.coupons.dto.User;
import com.oleg.coupons.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUsersDal extends CrudRepository<UserEntity, Integer> {

    @Query("SELECT new com.oleg.coupons.dto.User(u.id, u.username, u.password, u.userType, u.company.id) FROM UserEntity u WHERE u.id = :id")
    User getById(@Param("id") int id);

    @Query("SELECT new com.oleg.coupons.dto.User(u.id, u.username, u.password, u.userType, u.company.id) FROM UserEntity u")
    List<User> getAll();

    @Query("SELECT new com.oleg.coupons.dto.User(u.id, u.username, u.password, u.userType, u.company.id) FROM UserEntity u WHERE u.company.id = :companyId")
    List<User> getByCompanyId(@Param("companyId") int companyId);

    @Query("SELECT new com.oleg.coupons.dto.SuccessfulLoginDetails(u.id, u.userType, u.company.id) FROM UserEntity u WHERE username = :username AND password = :password")
    SuccessfulLoginDetails login(@Param("username") String username, @Param("password") String password);

    @Query("SELECT new com.oleg.coupons.dto.User(u.id, u.username, u.password, u.userType, u.company.id) FROM UserEntity u WHERE u.username = :username")
    User getByUsername(@Param("username") String username);

    @Query("SELECT new com.oleg.coupons.dto.User(u.id, u.username, u.password, u.userType, u.company.id) FROM UserEntity u WHERE u.company.id IN :companyIds AND (LOWER(u.username) LIKE %:searchText%)")
    Page<User> getComapnyTypeByFilters(
            @Param("companyIds") Integer[] companyIds,
            @Param("searchText") String searchText,
            Pageable pageable);

    @Query("SELECT new com.oleg.coupons.dto.User(u.id, u.username, u.password, u.userType, u.company.id) FROM UserEntity u WHERE (LOWER(u.username) LIKE %:searchText%)")
    Page<User> getByFilters(
            @Param("searchText") String searchText,
            Pageable pageable);

}
