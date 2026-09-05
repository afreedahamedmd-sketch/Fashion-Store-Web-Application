package com.fashionstore.dao.impl;

import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.model.ProductVariant;
import com.fashionstore.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductVariantDAOImpl implements ProductVariantDAO {

    // SQL Queries

    private static final String ADD_VARIANT =
            "INSERT INTO product_variants "
            + "(productId, size, stock) "
            + "VALUES (?, ?, ?)";

    private static final String GET_VARIANT_BY_ID =
            "SELECT * FROM product_variants WHERE variantId = ?";

    private static final String GET_VARIANTS_BY_PRODUCT_ID =
            "SELECT * FROM product_variants WHERE productId = ?";

    private static final String UPDATE_VARIANT =
            "UPDATE product_variants SET productId = ?, size = ?, stock = ? "
            + "WHERE variantId = ?";

    private static final String DELETE_VARIANT =
            "DELETE FROM product_variants WHERE variantId = ?";


    @Override
    public boolean addVariant(ProductVariant variant) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(ADD_VARIANT)) {

            statement.setInt(1, variant.getProductId());
            statement.setString(2, variant.getSize());
            statement.setInt(3, variant.getStock());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public ProductVariant getVariantById(int variantId) {

        ProductVariant variant = null;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(GET_VARIANT_BY_ID)) {

            statement.setInt(1, variantId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                variant = getVariantFromResultSet(resultSet);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return variant;
    }


    @Override
    public List<ProductVariant> getVariantsByProductId(int productId) {

        List<ProductVariant> variants = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(GET_VARIANTS_BY_PRODUCT_ID)) {

            statement.setInt(1, productId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                variants.add(getVariantFromResultSet(resultSet));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return variants;
    }


    @Override
    public boolean updateVariant(ProductVariant variant) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_VARIANT)) {

            statement.setInt(1, variant.getProductId());
            statement.setString(2, variant.getSize());
            statement.setInt(3, variant.getStock());
            statement.setInt(4, variant.getVariantId());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean deleteVariant(int variantId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_VARIANT)) {

            statement.setInt(1, variantId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    // Convert ResultSet into ProductVariant object

    private ProductVariant getVariantFromResultSet(ResultSet resultSet)
            throws Exception {

        ProductVariant variant = new ProductVariant();

        variant.setVariantId(
                resultSet.getInt("variantId")
        );

        variant.setProductId(
                resultSet.getInt("productId")
        );

        variant.setSize(
                resultSet.getString("size")
        );

        variant.setStock(
                resultSet.getInt("stock")
        );

        return variant;
    }
}