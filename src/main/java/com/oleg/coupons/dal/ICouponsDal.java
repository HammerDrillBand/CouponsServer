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

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.isAvailable, c.imageData) FROM CouponEntity c WHERE c.price < :price")
    List<CouponToClient> getBelowPrice(@Param("price") float price);

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.isAvailable, c.imageData) FROM CouponEntity c WHERE c.company.id = :companyId")
    List<CouponToClient> getByCompanyId(@Param("companyId") int companyId);

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.isAvailable, c.imageData) FROM CouponEntity c WHERE c.category.id = :categoryId")
    List<CouponToClient> getByCategoryId(@Param("categoryId") int categoryId);

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.isAvailable, c.imageData) FROM CouponEntity c WHERE c.category.name = :categoryName")
    List<CouponToClient> getByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.isAvailable, c.imageData) FROM CouponEntity c WHERE c.isAvailable = true")
    List<CouponToClient> getAllAvailable();

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.isAvailable, c.imageData) FROM CouponEntity c WHERE c.company.id IN :companyIds AND c.category.id IN :categoryIds AND c.isAvailable = true AND c.price >= :minPrice AND c.price <= :maxPrice")
    Page<CouponToClient> getAvailableByFilters(
            @Param("categoryIds") int[] categoryIds,
            @Param("companyIds") int[] companyIds,
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
            Pageable pageable);

    @Query("SELECT new com.oleg.coupons.dto.CouponToClient(c.id, c.name, c.description, c.startDate, c.endDate, c.amount, c.price, c.category.id, c.category.name, c.company.id, c.company.name, c.isAvailable, c.imageData) FROM CouponEntity c WHERE c.company.id IN :companyIds AND c.category.id IN :categoryIds AND c.price >= :minPrice AND c.price <= :maxPrice")
    Page<CouponToClient> getAllByFilters(
            @Param("categoryIds") int[] categoryIds,
            @Param("companyIds") int[] companyIds,
            @Param("minPrice") Float minPrice,
            @Param("maxPrice") Float maxPrice,
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
