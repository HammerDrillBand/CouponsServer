package com.oleg.coupons.dal;

import com.oleg.coupons.dto.PurchaseToClient;
import com.oleg.coupons.entities.PurchaseEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IPurchasesDal extends CrudRepository<PurchaseEntity, Integer> {

    @Query("SELECT new com.oleg.coupons.dto.PurchaseToClient(p.id, p.coupon.id, p.user.id, p.amount, p.date, p.coupon.name, p.coupon.description, p.coupon.category.id, p.user.username, p.coupon.company.name, p.coupon.company.contactEmail) FROM PurchaseEntity p WHERE p.id = :id")
    PurchaseToClient getById(@Param("id") int id);

    @Query("SELECT new com.oleg.coupons.dto.PurchaseToClient(p.id, p.coupon.id, p.user.id, p.amount, p.date, p.coupon.name, p.coupon.description, p.coupon.category.id, p.user.username, p.coupon.company.name, p.coupon.company.contactEmail) FROM PurchaseEntity p")
    List<PurchaseToClient> getAll();

    @Query("SELECT new com.oleg.coupons.dto.PurchaseToClient(p.id, p.coupon.id, p.user.id, p.amount, p.date, p.coupon.name, p.coupon.description, p.coupon.category.id, p.user.username, p.coupon.company.name, p.coupon.company.contactEmail) FROM PurchaseEntity p WHERE p.coupon.company.id = :companyId")
    List<PurchaseToClient> getByCompanyId(@Param("companyId") int companyId);

    @Query("SELECT new com.oleg.coupons.dto.PurchaseToClient(p.id, p.coupon.id, p.user.id, p.amount, p.date, p.coupon.name, p.coupon.description, p.coupon.category.id, p.user.username, p.coupon.company.name, p.coupon.company.contactEmail) FROM PurchaseEntity p WHERE p.user.id = :userId")
    List<PurchaseToClient> getByUserId(@Param("userId") int userId);

    @Query("SELECT new com.oleg.coupons.dto.PurchaseToClient(p.id, p.coupon.id, p.user.id, p.amount, p.date, p.coupon.name, p.coupon.description, p.coupon.category.id, p.user.username, p.coupon.company.name, p.coupon.company.contactEmail) FROM PurchaseEntity p WHERE p.coupon.category.id = :categoryId")
    List<PurchaseToClient> getByCategory(@Param("categoryId") int categoryId);

    @Query("SELECT new com.oleg.coupons.dto.PurchaseToClient(p.id, p.coupon.id, p.user.id, p.amount, p.date, p.coupon.name, p.coupon.description, p.coupon.category.id, p.user.username, p.coupon.company.name, p.coupon.company.contactEmail) FROM PurchaseEntity p WHERE p.coupon.category.name = :categoryName")
    List<PurchaseToClient> getByCategory(@Param("categoryName") String categoryName);

    @Query("SELECT new com.oleg.coupons.dto.PurchaseToClient(p.id, p.coupon.id, p.user.id, p.amount, p.date, p.coupon.name, p.coupon.description, p.coupon.category.id, p.user.username, p.coupon.company.name, p.coupon.company.contactEmail) FROM PurchaseEntity p WHERE p.date >= :fromDate AND p.date<=:toDate")
    List<PurchaseToClient> getByDateRange(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);
}
