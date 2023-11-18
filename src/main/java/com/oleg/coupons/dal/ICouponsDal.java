package com.oleg.coupons.dal;

import com.oleg.coupons.dto.CouponToClient;
import com.oleg.coupons.entities.CouponEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICouponsDal extends CrudRepository<CouponEntity, Integer> {

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.imageData) FROM CouponEntity c WHERE c.id = :id")
    CouponToClient getById(@Param("id") int id);

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.imageData) FROM CouponEntity c")
    List<CouponToClient> getAll();

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.imageData) FROM CouponEntity c WHERE c.price < :price")
    List<CouponToClient> getBelowPrice(@Param("price") float price);

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.imageData) FROM CouponEntity c WHERE c.company.id = :companyId")
    List<CouponToClient> getByCompanyId(@Param("companyId") int companyId);

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.imageData) FROM CouponEntity c WHERE c.category.id = :categoryId")
    List<CouponToClient> getByCategoryId(@Param("categoryId") int categoryId);

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.imageData) FROM CouponEntity c WHERE c.category.name = :categoryName")
    List<CouponToClient> getByCategoryName(@Param("categoryName") String categoryName);

    @Query("FROM CouponEntity c WHERE c.endDate < current_date()")
    List<CouponEntity> getExpiredCoupons();

    @Query("SELECT c.isAvailable FROM CouponEntity c WHERE c.id = :id")
    boolean isAvailable(@Param("id") int id);
}
