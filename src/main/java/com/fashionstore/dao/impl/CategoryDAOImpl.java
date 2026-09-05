package com.fashionstore.dao.impl;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.model.Category;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {

    // SQL Queries

    private static final String ADD_CATEGORY =
            "INSERT INTO categories (categoryName, description) "
            + "VALUES (?, ?)";

    private static final String GET_CATEGORY_BY_ID =
            "SELECT * FROM categories WHERE categoryId = ?";

    private static final String GET_ALL_CATEGORIES =
            "SELECT * FROM categories";

    private static final String UPDATE_CATEGORY =
            "UPDATE categories SET categoryName = ?, description = ? "
            + "WHERE categoryId = ?";

    private static final String DELETE_CATEGORY =
            "DELETE FROM categories WHERE categoryId = ?";


    @Override
    public boolean addCategory(Category category) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(ADD_CATEGORY)) {

            statement.setString(1, category.getCategoryName());
            statement.setString(2, category.getDescription());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public Category getCategoryById(int categoryId) {

        Category category = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(GET_CATEGORY_BY_ID)) {

            statement.setInt(1, categoryId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                category = getCategoryFromResultSet(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return category;
    }


    @Override
    public List<Category> getAllCategories() {

        List<Category> categories = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(GET_ALL_CATEGORIES);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                categories.add(getCategoryFromResultSet(resultSet));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }


    @Override
    public boolean updateCategory(Category category) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_CATEGORY)) {

            statement.setString(1, category.getCategoryName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getCategoryId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean deleteCategory(int categoryId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_CATEGORY)) {

            statement.setInt(1, categoryId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // Convert ResultSet into Category object

    private Category getCategoryFromResultSet(ResultSet resultSet)
            throws Exception {

        Category category = new Category();

        category.setCategoryId(
                resultSet.getInt("categoryId")
        );

        category.setCategoryName(
                resultSet.getString("categoryName")
        );

        category.setDescription(
                resultSet.getString("description")
        );

        return category;
    }
}