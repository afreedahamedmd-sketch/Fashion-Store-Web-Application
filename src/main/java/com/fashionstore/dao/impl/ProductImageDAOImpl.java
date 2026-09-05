package com.fashionstore.dao.impl;

import com.fashionstore.dao.ProductImageDAO;
import com.fashionstore.model.ProductImage;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductImageDAOImpl implements ProductImageDAO {

    // SQL Queries

    private static final String ADD_IMAGE =
            "INSERT INTO product_images "
            + "(imagePath, productId, isPrimary) "
            + "VALUES (?, ?, ?)";

    private static final String GET_IMAGE_BY_ID =
            "SELECT * FROM product_images WHERE imageId = ?";

    private static final String GET_IMAGES_BY_PRODUCT_ID =
            "SELECT * FROM product_images WHERE productId = ?";

    private static final String UPDATE_IMAGE =
            "UPDATE product_images SET imagePath = ?, "
            + "productId = ?, isPrimary = ? "
            + "WHERE imageId = ?";

    private static final String DELETE_IMAGE =
            "DELETE FROM product_images WHERE imageId = ?";


    @Override
    public boolean addImage(ProductImage image) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(ADD_IMAGE)) {

            statement.setString(1, image.getImagePath());
            statement.setInt(2, image.getProductId());
            statement.setBoolean(3, image.isPrimary());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public ProductImage getImageById(int imageId) {

        ProductImage image = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(GET_IMAGE_BY_ID)) {

            statement.setInt(1, imageId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                image = getImageFromResultSet(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }


    @Override
    public List<ProductImage> getImagesByProductId(int productId) {

        List<ProductImage> images = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(GET_IMAGES_BY_PRODUCT_ID)) {

            statement.setInt(1, productId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                images.add(getImageFromResultSet(resultSet));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return images;
    }


    @Override
    public boolean updateImage(ProductImage image) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_IMAGE)) {

            statement.setString(1, image.getImagePath());
            statement.setInt(2, image.getProductId());
            statement.setBoolean(3, image.isPrimary());
            statement.setInt(4, image.getImageId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean deleteImage(int imageId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_IMAGE)) {

            statement.setInt(1, imageId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // Convert ResultSet into ProductImage object

    private ProductImage getImageFromResultSet(ResultSet resultSet)
            throws Exception {

        ProductImage image = new ProductImage();

        image.setImageId(
                resultSet.getInt("imageId")
        );

        image.setProductId(
                resultSet.getInt("productId")
        );

        image.setImagePath(
                resultSet.getString("imagePath")
        );

        image.setPrimary(
                resultSet.getBoolean("isPrimary")
        );

        return image;
    }
}