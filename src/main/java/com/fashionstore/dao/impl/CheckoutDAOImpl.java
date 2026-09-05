package com.fashionstore.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.fashionstore.dao.CheckoutDAO;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Order;

import com.fashionstore.util.DBConnection;

public class CheckoutDAOImpl implements CheckoutDAO {

    @Override
    public int placeOrder(
            Order order,
            int cartId,
            BigDecimal subtotal,
            BigDecimal shipping,
            BigDecimal total) {

        Connection connection = null;

        String getCartItemsSQL =
                "SELECT ci.variantId, ci.quantity, " +
                "       pv.stock, p.price " +
                "FROM cart_items ci " +
                "JOIN product_variants pv ON ci.variantId = pv.variantId " +
                "JOIN products p ON pv.productId = p.productId " +
                "WHERE ci.cartId = ? " +
                "FOR UPDATE";

        String insertOrderSQL =
                "INSERT INTO orders " +
                "(userId, totalAmount, status, paymentMethod, " +
                " shippingAddress, city, state, pincode) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String insertOrderItemSQL =
                "INSERT INTO order_items " +
                "(orderId, variantId, quantity, price) " +
                "VALUES (?, ?, ?, ?)";

        String updateStockSQL =
                "UPDATE product_variants " +
                "SET stock = stock - ? " +
                "WHERE variantId = ? AND stock >= ?";

        String clearCartSQL =
                "DELETE FROM cart_items WHERE cartId = ?";

        try {
            connection = DBConnection.getConnection();

            if (connection == null) {
                return -1;
            }

            connection.setAutoCommit(false);

            /*
             * Step 1:
             * Lock the cart items and read the current
             * stock and price directly from the database.
             */
            try (PreparedStatement cartStatement =
                         connection.prepareStatement(getCartItemsSQL)) {

                cartStatement.setInt(1, cartId);

                try (ResultSet rs = cartStatement.executeQuery()) {

                    if (!rs.next()) {
                        connection.rollback();
                        return -1;
                    }

                    /*
                     * We need to process the ResultSet twice logically:
                     * first for validation/calculation and later for insertion.
                     *
                     * Since the ResultSet cannot be safely reused after
                     * another statement is executed, store the required
                     * information in memory first.
                     */

                    java.util.List<Integer> variantIds =
                            new java.util.ArrayList<>();

                    java.util.List<Integer> quantities =
                            new java.util.ArrayList<>();

                    java.util.List<BigDecimal> prices =
                            new java.util.ArrayList<>();

                    BigDecimal calculatedSubtotal = BigDecimal.ZERO;

                    do {
                        int variantId = rs.getInt("variantId");
                        int quantity = rs.getInt("quantity");
                        int stock = rs.getInt("stock");
                        BigDecimal price = rs.getBigDecimal("price");

                        if (quantity <= 0) {
                            connection.rollback();
                            return -1;
                        }

                        if (stock < quantity) {
                            connection.rollback();
                            return -1;
                        }

                        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
                            connection.rollback();
                            return -1;
                        }

                        variantIds.add(variantId);
                        quantities.add(quantity);
                        prices.add(price);

                        calculatedSubtotal = calculatedSubtotal.add(
                                price.multiply(BigDecimal.valueOf(quantity))
                        );

                    } while (rs.next());

                    /*
                     * Recalculate the total on the server.
                     * Do not trust subtotal/shipping/total values
                     * sent by the browser.
                     */
                    BigDecimal calculatedShipping;

                    if (calculatedSubtotal.compareTo(
                            new BigDecimal("2000.00")) >= 0) {
                        calculatedShipping = BigDecimal.ZERO;
                    } else {
                        calculatedShipping = new BigDecimal("100.00");
                    }

                    BigDecimal calculatedTotal =
                            calculatedSubtotal.add(calculatedShipping);

                    /*
                     * Step 2:
                     * Create the order.
                     */
                    int orderId;

                    try (PreparedStatement orderStatement =
                                 connection.prepareStatement(
                                         insertOrderSQL,
                                         PreparedStatement.RETURN_GENERATED_KEYS)) {

                        orderStatement.setInt(1, order.getUserId());
                        orderStatement.setBigDecimal(2, calculatedTotal);
                        orderStatement.setString(3, "Pending");
                        orderStatement.setString(4, order.getPaymentMethod());
                        orderStatement.setString(5, order.getShippingAddress());
                        orderStatement.setString(6, order.getCity());
                        orderStatement.setString(7, order.getState());
                        orderStatement.setString(8, order.getPincode());

                        int affectedRows = orderStatement.executeUpdate();

                        if (affectedRows == 0) {
                            connection.rollback();
                            return -1;
                        }

                        try (ResultSet generatedKeys =
                                     orderStatement.getGeneratedKeys()) {

                            if (!generatedKeys.next()) {
                                connection.rollback();
                                return -1;
                            }

                            orderId = generatedKeys.getInt(1);
                        }
                    }

                    /*
                     * Step 3:
                     * Create order_items records.
                     */
                    try (PreparedStatement itemStatement =
                                 connection.prepareStatement(insertOrderItemSQL)) {

                        for (int i = 0; i < variantIds.size(); i++) {

                            itemStatement.setInt(1, orderId);
                            itemStatement.setInt(2, variantIds.get(i));
                            itemStatement.setInt(3, quantities.get(i));
                            itemStatement.setBigDecimal(4, prices.get(i));

                            itemStatement.addBatch();
                        }

                        itemStatement.executeBatch();
                    }

                    /*
                     * Step 4:
                     * Reduce product stock.
                     */
                    try (PreparedStatement stockStatement =
                                 connection.prepareStatement(updateStockSQL)) {

                        for (int i = 0; i < variantIds.size(); i++) {

                            int quantity = quantities.get(i);
                            int variantId = variantIds.get(i);

                            stockStatement.setInt(1, quantity);
                            stockStatement.setInt(2, variantId);
                            stockStatement.setInt(3, quantity);

                            int affectedRows =
                                    stockStatement.executeUpdate();

                            if (affectedRows == 0) {
                                connection.rollback();
                                return -1;
                            }
                        }
                    }

                    /*
                     * Step 5:
                     * Clear the cart.
                     */
                    try (PreparedStatement clearCartStatement =
                                 connection.prepareStatement(clearCartSQL)) {

                        clearCartStatement.setInt(1, cartId);

                        clearCartStatement.executeUpdate();
                    }

                    /*
                     * Step 6:
                     * Everything succeeded.
                     */
                    connection.commit();

                    return orderId;
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();

            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            return -1;

        } finally {

            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}