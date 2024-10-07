package vn.com.openlab.api.category.service;

import vn.com.openlab.api.category.dto.CategoryDTO;
import vn.com.openlab.api.category.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO category);

    Category getCategoryById(Long categoryId);

    List<Category> getAllCategories();

    Category updateCategory(Long categoryId, CategoryDTO category);

    void deleteCategory(Long categoryId);
}

