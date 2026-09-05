package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.model.Cart;
import com.fashionstore.util.DBConnection;

public class CartDAOImpl implements CartDAO {
    private static final String INSERT_CART = "INSERT INTO cart (userId) VALUES (?)";
    private static final String SELECT_CART_BY_ID = "SELECT * FROM cart WHERE cartId = ?";
    private static final String SELECT_CART_BY_USER_ID = "SELECT * FROM cart WHERE userId = ?";
    private static final String UPDATE_CART = "UPDATE cart SET userId = ? WHERE cartId = ?";
    private static final String DELETE_CART = "DELETE FROM cart WHERE cartId = ?";

    @Override
    public boolean addCart(Cart cart) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CART)) {
            statement.setInt(1, cart.getUserId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Cart getCartById(int cartId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CART_BY_ID)) {
            statement.setInt(1, cartId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getCartFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cart getCartByUserId(int userId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_CART_BY_USER_ID)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getCartFromResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateCart(Cart cart) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CART)) {
            statement.setInt(1, cart.getUserId());
            statement.setInt(2, cart.getCartId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteCart(int cartId) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CART)) {
            statement.setInt(1, cartId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Cart getCartFromResultSet(ResultSet resultSet) throws SQLException {
        Cart cart = new Cart();
        cart.setCartId(resultSet.getInt("cartId"));
        cart.setUserId(resultSet.getInt("userId"));
        if (resultSet.getTimestamp("createdDate") != null) {
            cart.setCreatedDate(resultSet.getTimestamp("createdDate").toLocalDateTime());
        }
        if (resultSet.getTimestamp("updatedDate") != null) {
            cart.setUpdatedDate(resultSet.getTimestamp("updatedDate").toLocalDateTime());
        }
        return cart;
    }
}