package vn.com.openlab.service;

import vn.com.openlab.dto.CategoryDTO;
import vn.com.openlab.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO category);

    Category getCategoryById(Long categoryId);

    List<Category> getAllCategories();

    Category updateCategory(Long categoryId, CategoryDTO category);

    void deleteCategory(Long categoryId);
}

