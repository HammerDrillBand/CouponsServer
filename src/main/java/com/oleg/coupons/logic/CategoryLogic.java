package com.oleg.coupons.logic;

import com.oleg.coupons.dal.ICategoriesDal;
import com.oleg.coupons.dto.CategoriesPageResult;
import com.oleg.coupons.dto.Category;
import com.oleg.coupons.entities.CategoryEntity;
import com.oleg.coupons.enums.ErrorType;
import com.oleg.coupons.exceptions.ApplicationException;
import com.oleg.coupons.utils.CommonValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryLogic {
    private ICategoriesDal categoriesDal;

    @Autowired
    public CategoryLogic(ICategoriesDal categoriesDal) {
        this.categoriesDal = categoriesDal;
    }

    public int addCategory(Category category) throws ApplicationException {
        validateCategoryName(category.getName());
        CategoryEntity categoryEntity = new CategoryEntity(category);
        try {
            categoryEntity = this.categoriesDal.save(categoryEntity);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to add category", e);
        }
        return categoryEntity.getId();
    }

    public void updateCategory(Category category) throws ApplicationException {
        validateCategoryName(category.getName());
        CategoryEntity categoryEntity = new CategoryEntity(category);
        try {
            this.categoriesDal.save(categoryEntity);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to update category", e);
        }
    }

    public void deleteCategory(int id) throws ApplicationException {
        if (!isCategoryExist(id)) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the category to be deleted");
        }
        try {
            this.categoriesDal.deleteById(id);
        } catch (Exception e) {
            throw new ApplicationException(ErrorType.GENERAL_ERROR, "Failed to delete the company with id " + id, e);
        }
    }

    public List<Category> getAll() throws ApplicationException {
        List<Category> categories = this.categoriesDal.getAll();
        if (categories == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the categories you were looking for");
        }
        return categories;
    }

    public CategoriesPageResult getByFilters(int page, String searchText) throws ApplicationException {
        int categoriesPerPage = 20;
        int adjustedPage = page - 1;

        searchText = searchText.toLowerCase();

        Pageable pageable = PageRequest.of(adjustedPage, categoriesPerPage);
        Page<Category> categoriesPage = this.categoriesDal.getByFilters(searchText, pageable);
        if (categoriesPage == null) {
            throw new ApplicationException(ErrorType.COULD_NOT_FIND, "Could not find the categories you were looking for");
        }
        int totalPages = categoriesPage.getTotalPages();
        List<Category> categories = categoriesPage.getContent();

        CategoriesPageResult categoriesPageResult = new CategoriesPageResult(categories, totalPages);

        return categoriesPageResult;
    }

    private void validateCategoryName(String name) throws ApplicationException {
        int validationResult = CommonValidations.validateStringLength(name, 2, 45);
        if (validationResult == 1) {
            throw new ApplicationException(ErrorType.CATEGORY_NAME_TOO_LONG);
        }
        if (validationResult == -1) {
            throw new ApplicationException(ErrorType.CATEGORY_NAME_TOO_SHORT);
        }
    }

    boolean isCategoryExist(int id) {
        return this.categoriesDal.existsById(id);
    }

    public Integer[] getAllCategoryIds() {
        return this.categoriesDal.getAllCategoryIds();
    }

}
