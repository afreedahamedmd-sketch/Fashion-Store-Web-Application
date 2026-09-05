package com.fashionstore.dao;

import java.math.BigDecimal;

import com.fashionstore.model.Order;

public interface CheckoutDAO {

    int placeOrder(
            Order order,
            int cartId,
            BigDecimal subtotal,
            BigDecimal shipping,
            BigDecimal total);
}