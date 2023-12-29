package com.oleg.coupons.dal;

import com.oleg.coupons.dto.PurchaseToClient;
import com.oleg.coupons.entities.PurchaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IPurchasesDal extends CrudRepository<PurchaseEntity, Integer> {

    @Query("SELECT new com.oleg.coupons.dto.PurchaseToClient(p.id, p.coupon.id, p.user.id, p.amount, p.date, p.coupon.name, p.coupon.description, p.coupon.category.id, p.coupon.category.name, p.user.username, p.coupon.company.id, p.coupon.company.name) FROM PurchaseEntity p WHERE p.id = :id")
    PurchaseToClient getById(@Param("id") int id);

    @Query("SELECT new com.oleg.coupons.dto.PurchaseToClient(p.id, p.coupon.id, p.user.id, p.amount, p.date, p.coupon.name, p.coupon.description, p.coupon.category.id, p.coupon.category.name, p.user.username, p.coupon.company.id, p.coupon.company.name) FROM PurchaseEntity p WHERE p.coupon.company.id IN :companyIds AND p.coupon.category.id IN :categoryIds AND (LOWER(p.coupon.name) LIKE %:searchText% OR LOWER(p.coupon.description) LIKE %:searchText%)")
    Page<PurchaseToClient> getByFilters(@Param("companyIds") Integer[] companyIds,
                                        @Param("categoryIds") Integer[] categoryIds,
                                        @Param("searchText") String searchText,
                                        Pageable pageable);

    @Query("SELECT new com.oleg.coupons.dto.PurchaseToClient(p.id, p.coupon.id, p.user.id, p.amount, p.date, p.coupon.name, p.coupon.description, p.coupon.category.id, p.coupon.category.name, p.user.username, p.coupon.company.id, p.coupon.company.name) FROM PurchaseEntity p WHERE p.user.id = :userId AND p.coupon.company.id IN :companyIds AND p.coupon.category.id IN :categoryIds AND (LOWER(p.coupon.name) LIKE %:searchText% OR LOWER(p.coupon.description) LIKE %:searchText%)")
    Page<PurchaseToClient> getCustomerPurchasesByFilters(@Param("userId") int userId,
                                                         @Param("companyIds") Integer[] companyIds,
                                                         @Param("categoryIds") Integer[] categoryIds,
                                                         @Param("searchText") String searchText,
                                                         Pageable pageable);
}
