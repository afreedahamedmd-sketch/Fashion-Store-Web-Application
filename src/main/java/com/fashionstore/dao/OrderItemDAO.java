package com.fashionstore.dao;

import java.util.List;

import com.fashionstore.model.OrderItem;

public interface OrderItemDAO {

    boolean addOrderItem(OrderItem orderItem);

    OrderItem getOrderItemById(int orderItemId);

    List<OrderItem> getOrderItemsByOrderId(int orderId);

    boolean updateOrderItem(OrderItem orderItem);

    boolean deleteOrderItem(int orderItemId);
}