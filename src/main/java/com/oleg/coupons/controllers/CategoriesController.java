package com.oleg.coupons.controllers;

import com.oleg.coupons.dto.Category;
import com.oleg.coupons.exceptions.ApplicationException;
import com.oleg.coupons.logic.CategoryLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private CategoryLogic categoryLogic;

    @Autowired
    public CategoriesController(CategoryLogic categoryLogic) {
        this.categoryLogic = categoryLogic;
    }

    @PostMapping
    public int addCategory(@RequestBody Category category) throws ApplicationException {
        return this.categoryLogic.addCategory(category);
    }

    @PutMapping
    public void updateCategory(@RequestBody Category category) throws ApplicationException {
        this.categoryLogic.updateCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") int id) throws ApplicationException {
        this.categoryLogic.deleteCategory(id);
    }

    @GetMapping
    public List<Category> getAllCategories() throws ApplicationException {
        return this.categoryLogic.getAll();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable("id") int id) throws ApplicationException {
        return this.categoryLogic.getById(id);
    }
}
