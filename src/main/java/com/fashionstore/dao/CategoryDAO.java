package com.fashionstore.dao;

import com.fashionstore.model.Category;
import java.util.List;

public interface CategoryDAO {

    boolean addCategory(Category category);

    Category getCategoryById(int categoryId);

    List<Category> getAllCategories();

    boolean updateCategory(Category category);

    boolean deleteCategory(int categoryId);
}