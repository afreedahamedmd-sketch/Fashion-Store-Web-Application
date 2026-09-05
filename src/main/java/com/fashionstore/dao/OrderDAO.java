package com.fashionstore.dao;

import java.util.List;

import com.fashionstore.model.Order;

public interface OrderDAO {

    boolean addOrder(Order order);

    Order getOrderById(int orderId);

    List<Order> getOrdersByUserId(int userId);

    boolean updateOrder(Order order);

    boolean deleteOrder(int orderId);
}