package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

public class CartItemDAOImpl implements CartItemDAO {
    private static final String INSERT_CART_ITEM = "INSERT INTO cart_items (cartId, variantId, quantity) VALUES (?, ?, ?)";
    private static final String SELECT_CART_ITEM_BY_ID = "SELECT * FROM cart_items WHERE cartItemId = ?";
    private static final String SELECT_CART_ITEMS_BY_CART_ID = "SELECT * FROM cart_items WHERE cartId = ? ORDER BY cartItemId DESC";
    private static final String SELECT_CART_ITEMS_WITH_DETAILS = "SELECT ci.cartItemId, ci.cartId, ci.variantId, ci.quantity, pv.productId, pv.size, pv.stock, p.productName, p.description, p.price, p.discount, p.gender, p.brand, pi.imagePath FROM cart_items ci JOIN product_variants pv ON ci.variantId = pv.variantId JOIN products p ON pv.productId = p.productId LEFT JOIN product_images pi ON p.productId = pi.productId AND pi.isPrimary = TRUE WHERE ci.cartId = ? ORDER BY ci.cartItemId DESC";
    private static final String UPDATE_CART_ITEM = "UPDATE cart_items SET variantId = ?, quantity = ? WHERE cartItemId = ?";
    private static final String DELETE_CART_ITEM = "DELETE FROM cart_items WHERE cartItemId = ?";

    @Override
    public boolean addCartItem(CartItem cartItem) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CART_ITEM)) {
            statement.setInt(1, cartItem.getCartId());
            statement.setInt(2, cartItem.getVariantId());
            statement.setInt(3, cartItem.getQuantity());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public CartItem getCartItemById(int cartItemId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CART_ITEM_BY_ID)) {
            statement.setInt(1, cartItemId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getCartItemFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) {
        List<CartItem> cartItems = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CART_ITEMS_BY_CART_ID)) {
            statement.setInt(1, cartId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cartItems.add(getCartItemFromResultSet(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cartItems;
    }

    @Override
    public List<CartItem> getCartItemsWithProductDetails(int cartId) {
        List<CartItem> cartItems = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CART_ITEMS_WITH_DETAILS)) {
            statement.setInt(1, cartId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    CartItem cartItem = getCartItemFromResultSet(resultSet);
                    cartItem.setProductId(resultSet.getInt("productId"));
                    cartItem.setProductName(resultSet.getString("productName"));
                    cartItem.setDescription(resultSet.getString("description"));
                    cartItem.setPrice(resultSet.getBigDecimal("price"));
                    cartItem.setDiscount(resultSet.getBigDecimal("discount"));
                    cartItem.setGender(resultSet.getString("gender"));
                    cartItem.setBrand(resultSet.getString("brand"));
                    cartItem.setSize(resultSet.getString("size"));
                    cartItem.setStock(resultSet.getInt("stock"));
                    cartItem.setImagePath(resultSet.getString("imagePath"));
                    cartItems.add(cartItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    @Override
    public boolean updateCartItem(CartItem cartItem) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CART_ITEM)) {
            statement.setInt(1, cartItem.getVariantId());
            statement.setInt(2, cartItem.getQuantity());
            statement.setInt(3, cartItem.getCartItemId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCartItem(int cartItemId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CART_ITEM)) {
            statement.setInt(1, cartItemId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private CartItem getCartItemFromResultSet(ResultSet resultSet) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(resultSet.getInt("cartItemId"));
        cartItem.setCartId(resultSet.getInt("cartId"));
        cartItem.setVariantId(resultSet.getInt("variantId"));
        cartItem.setQuantity(resultSet.getInt("quantity"));
        return cartItem;
    }
}