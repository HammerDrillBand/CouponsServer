package com.oleg.coupons.dal;

import com.oleg.coupons.dto.CouponToClient;
import com.oleg.coupons.entities.CouponEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICouponsDal extends CrudRepository<CouponEntity, Integer> {

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.isAvailable, c.imageData) FROM CouponEntity c WHERE c.id = :id")
    CouponToClient getById(@Param("id") int id);

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.isAvailable, c.imageData) FROM CouponEntity c")
    List<CouponToClient> getAll();

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.isAvailable, c.imageData) FROM CouponEntity c WHERE c.company.id IN :companyIds AND c.category.id IN :categoryIds AND c.isAvailable = true AND c.price >= :minPrice AND c.price <= :maxPrice AND (LOWER(c.name) LIKE %:searchText% OR LOWER(c.description) LIKE %:searchText%)")
    Page<CouponToClient> getAvailableByFilters(
            @Param("categoryIds") Integer[] categoryIds,
            @Param("companyIds") Integer[] companyIds,
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
            @Param("searchText") String searchText,
            Pageable pageable);

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.isAvailable, c.imageData) FROM CouponEntity c WHERE c.company.id IN :companyIds AND c.category.id IN :categoryIds AND c.price >= :minPrice AND c.price <= :maxPrice AND (LOWER(c.name) LIKE %:searchText% OR LOWER(c.description) LIKE %:searchText%)")
    Page<CouponToClient> getAllByFilters(
            @Param("categoryIds") Integer[] categoryIds,
            @Param("companyIds") Integer[] companyIds,
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
            @Param("searchText") String searchText,
            Pageable pageable);

    @Query("FROM CouponEntity c WHERE c.endDate < current_date()")
    List<CouponEntity> getExpiredCoupons();

    @Query("SELECT c.isAvailable FROM CouponEntity c WHERE c.id = :id")
    boolean isAvailable(@Param("id") int id);

    @Query("SELECT MIN(c.price) FROM CouponEntity c")
    Float getMinPrice();

    @Query("SELECT MIN(c.price) FROM CouponEntity c WHERE c.company.id = :companyId")
    Float getMinPriceByCompany(@Param("companyId") int companyId);

    @Query("SELECT MAX(c.price) FROM CouponEntity c")
    Float getMaxPrice();

    @Query("SELECT MAX(c.price) FROM CouponEntity c WHERE c.company.id = :companyId")
    Float getMaxPriceByCompany(@Param("companyId") int companyId);
}
