package com.oleg.coupons.dal;

import com.oleg.coupons.dto.Category;
import com.oleg.coupons.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICategoriesDal extends CrudRepository<CategoryEntity, Integer> {

    @Query("SELECT new com.oleg.coupons.dto.Category(c.id, c.name) FROM CategoryEntity c WHERE c.id = :id")
    Category getById(@Param("id") int id);

    @Query("SELECT new com.oleg.coupons.dto.Category(c.id, c.name) FROM CategoryEntity c")
    List<Category> getAll();

    @Query("SELECT new com.oleg.coupons.dto.Category(c.id, c.name) FROM CategoryEntity c")
    Page<Category> getByPage(Pageable pageable);
}
