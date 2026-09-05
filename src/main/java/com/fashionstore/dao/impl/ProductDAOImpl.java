package com.fashionstore.dao.impl;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.model.Product;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    // SQL Queries

    private static final String ADD_PRODUCT =
            "INSERT INTO products "
            + "(categoryId, productName, description, price, discount, gender, brand, isAvailable) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String GET_PRODUCT_BY_ID =
            "SELECT * FROM products WHERE productId = ?";

    private static final String GET_ALL_PRODUCTS =
            "SELECT * FROM products";

    private static final String UPDATE_PRODUCT =
            "UPDATE products SET categoryId = ?, productName = ?, "
            + "description = ?, price = ?, discount = ?, gender = ?, "
            + "brand = ?, isAvailable = ? "
            + "WHERE productId = ?";

    private static final String DELETE_PRODUCT =
            "DELETE FROM products WHERE productId = ?";


    @Override
    public boolean addProduct(Product product) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(ADD_PRODUCT)) {

            statement.setInt(1, product.getCategoryId());
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getDescription());
            statement.setBigDecimal(4, product.getPrice());
            statement.setBigDecimal(5, product.getDiscount());
            statement.setString(6, product.getGender());
            statement.setString(7, product.getBrand());
            statement.setBoolean(8, product.isAvailable());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public Product getProductById(int productId) {

        Product product = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(GET_PRODUCT_BY_ID)) {

            statement.setInt(1, productId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                product = getProductFromResultSet(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return product;
    }


    @Override
    public List<Product> getAllProducts() {

        List<Product> products = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(GET_ALL_PRODUCTS);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                products.add(getProductFromResultSet(resultSet));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public List<Product> filterProducts(String keyword, Integer categoryId, String gender, Double minPrice, Double maxPrice, String size, String sort) {
        List<Product> products = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "SELECT DISTINCT p.* FROM products p " +
            "LEFT JOIN product_variants pv ON p.productId = pv.productId " +
            "WHERE p.isAvailable = TRUE"
        );
        List<Object> parameters = new ArrayList<>();

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql.append(" AND (p.productName LIKE ? OR p.description LIKE ?)");
            String keywordValue = "%" + keyword.trim() + "%";
            parameters.add(keywordValue);
            parameters.add(keywordValue);
        }
        if (categoryId != null) {
            sql.append(" AND p.categoryId = ?");
            parameters.add(categoryId);
        }
        if (gender != null && !gender.trim().isEmpty()) {
            sql.append(" AND p.gender = ?");
            parameters.add(gender);
        }
        if (minPrice != null) {
            sql.append(" AND p.price >= ?");
            parameters.add(minPrice);
        }
        if (maxPrice != null) {
            sql.append(" AND p.price <= ?");
            parameters.add(maxPrice);
        }
        if (size != null && !size.trim().isEmpty()) {
            sql.append(" AND pv.size = ? AND pv.stock > 0");
            parameters.add(size);
        }

        if ("price-low".equals(sort)) {
            sql.append(" ORDER BY p.price ASC");
        } else if ("price-high".equals(sort)) {
            sql.append(" ORDER BY p.price DESC");
        } else if ("name".equals(sort)) {
            sql.append(" ORDER BY p.productName ASC");
        } else {
            sql.append(" ORDER BY p.createdDate DESC");
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {

            for (int i = 0; i < parameters.size(); i++) {
                statement.setObject(i + 1, parameters.get(i));
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    try {
						products.add(getProductFromResultSet(resultSet));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }


    @Override
    public boolean updateProduct(Product product) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_PRODUCT)) {

            statement.setInt(1, product.getCategoryId());
            statement.setString(2, product.getProductName());
            statement.setString(3, product.getDescription());
            statement.setBigDecimal(4, product.getPrice());
            statement.setBigDecimal(5, product.getDiscount());
            statement.setString(6, product.getGender());
            statement.setString(7, product.getBrand());
            statement.setBoolean(8, product.isAvailable());
            statement.setInt(9, product.getProductId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean deleteProduct(int productId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_PRODUCT)) {

            statement.setInt(1, productId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // Convert ResultSet into Product object

    private Product getProductFromResultSet(ResultSet resultSet)
            throws Exception {

        Product product = new Product();

        product.setProductId(
                resultSet.getInt("productId")
        );

        product.setCategoryId(
                resultSet.getInt("categoryId")
        );

        product.setProductName(
                resultSet.getString("productName")
        );

        product.setDescription(
                resultSet.getString("description")
        );

        product.setPrice(
                resultSet.getBigDecimal("price")
        );

        product.setDiscount(
                resultSet.getBigDecimal("discount")
        );

        product.setGender(
                resultSet.getString("gender")
        );

        product.setBrand(
                resultSet.getString("brand")
        );

        product.setAvailable(
                resultSet.getBoolean("isAvailable")
        );

        if (resultSet.getTimestamp("createdDate") != null) {
            product.setCreatedDate(
                    resultSet.getTimestamp("createdDate").toLocalDateTime()
            );
        }

        return product;
    }
}