package com.fashionstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.model.Order;
import com.fashionstore.util.DBConnection;

public class OrderDAOImpl implements OrderDAO {

    private static final String INSERT_ORDER =
            "INSERT INTO orders " +
            "(userId, totalAmount, status, paymentMethod, shippingAddress, city, state, pincode) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ORDER_BY_ID =
            "SELECT * FROM orders WHERE orderId = ?";

    private static final String SELECT_ORDERS_BY_USER_ID =
            "SELECT * FROM orders WHERE userId = ? ORDER BY orderDate DESC";

    private static final String UPDATE_ORDER =
            "UPDATE orders SET totalAmount = ?, status = ?, paymentMethod = ?, " +
            "shippingAddress = ?, city = ?, state = ?, pincode = ? " +
            "WHERE orderId = ?";

    private static final String DELETE_ORDER =
            "DELETE FROM orders WHERE orderId = ?";


    @Override
    public boolean addOrder(Order order) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_ORDER)) {

            statement.setInt(1, order.getUserId());
            statement.setBigDecimal(2, order.getTotalAmount());
            statement.setString(3, order.getStatus());
            statement.setString(4, order.getPaymentMethod());
            statement.setString(5, order.getShippingAddress());
            statement.setString(6, order.getCity());
            statement.setString(7, order.getState());
            statement.setString(8, order.getPincode());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public Order getOrderById(int orderId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ORDER_BY_ID)) {

            statement.setInt(1, orderId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return getOrderFromResultSet(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public List<Order> getOrdersByUserId(int userId) {

        List<Order> orders = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SELECT_ORDERS_BY_USER_ID)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    orders.add(getOrderFromResultSet(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }


    @Override
    public boolean updateOrder(Order order) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER)) {

            statement.setBigDecimal(1, order.getTotalAmount());
            statement.setString(2, order.getStatus());
            statement.setString(3, order.getPaymentMethod());
            statement.setString(4, order.getShippingAddress());
            statement.setString(5, order.getCity());
            statement.setString(6, order.getState());
            statement.setString(7, order.getPincode());
            statement.setInt(8, order.getOrderId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    @Override
    public boolean deleteOrder(int orderId) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(DELETE_ORDER)) {

            statement.setInt(1, orderId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    private Order getOrderFromResultSet(ResultSet resultSet)
            throws SQLException {

        Order order = new Order();

        order.setOrderId(resultSet.getInt("orderId"));
        order.setUserId(resultSet.getInt("userId"));

        if (resultSet.getTimestamp("orderDate") != null) {
            order.setOrderDate(
                    resultSet.getTimestamp("orderDate").toLocalDateTime()
            );
        }

        order.setTotalAmount(resultSet.getBigDecimal("totalAmount"));
        order.setStatus(resultSet.getString("status"));
        order.setPaymentMethod(resultSet.getString("paymentMethod"));
        order.setShippingAddress(resultSet.getString("shippingAddress"));
        order.setCity(resultSet.getString("city"));
        order.setState(resultSet.getString("state"));
        order.setPincode(resultSet.getString("pincode"));

        return order;
    }
}