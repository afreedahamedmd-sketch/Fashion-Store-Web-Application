package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.model.OrderItem;
import com.fashionstore.util.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

    private static final String INSERT_ORDER_ITEM =
            "INSERT INTO order_items " +
            "(orderId, variantId, quantity, price) " +
            "VALUES (?, ?, ?, ?)";

    private static final String SELECT_ORDER_ITEM_BY_ID =
            "SELECT * FROM order_items WHERE orderItemId = ?";

    private static final String SELECT_ORDER_ITEMS_BY_ORDER_ID =
            "SELECT * FROM order_items WHERE orderId = ? ORDER BY orderItemId ASC";

    private static final String UPDATE_ORDER_ITEM =
            "UPDATE order_items SET variantId = ?, quantity = ?, price = ? " +
            "WHERE orderItemId = ?";

    private static final String DELETE_ORDER_ITEM =
            "DELETE FROM order_items WHERE orderItemId = ?";


    @Override
    public boolean addOrderItem(OrderItem orderItem) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(INSERT_ORDER_ITEM)) {

            statement.setInt(1, orderItem.getOrderId());
            statement.setInt(2, orderItem.getVariantId());
            statement.setInt(3, orderItem.getQuantity());
            statement.setBigDecimal(4, orderItem.getPrice());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public OrderItem getOrderItemById(int orderItemId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SELECT_ORDER_ITEM_BY_ID)) {

            statement.setInt(1, orderItemId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return getOrderItemFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {

        List<OrderItem> orderItems = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(
                             SELECT_ORDER_ITEMS_BY_ORDER_ID)) {

            statement.setInt(1, orderId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    orderItems.add(getOrderItemFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItems;
    }


    @Override
    public boolean updateOrderItem(OrderItem orderItem) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(UPDATE_ORDER_ITEM)) {

            statement.setInt(1, orderItem.getVariantId());
            statement.setInt(2, orderItem.getQuantity());
            statement.setBigDecimal(3, orderItem.getPrice());
            statement.setInt(4, orderItem.getOrderItemId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean deleteOrderItem(int orderItemId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_ORDER_ITEM)) {

            statement.setInt(1, orderItemId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    private OrderItem getOrderItemFromResultSet(ResultSet resultSet)
            throws SQLException {

        OrderItem orderItem = new OrderItem();

        orderItem.setOrderItemId(
                resultSet.getInt("orderItemId"));

        orderItem.setOrderId(
                resultSet.getInt("orderId"));

        orderItem.setVariantId(
                resultSet.getInt("variantId"));

        orderItem.setQuantity(
                resultSet.getInt("quantity"));

        orderItem.setPrice(
                resultSet.getBigDecimal("price"));

        return orderItem;
    }
}