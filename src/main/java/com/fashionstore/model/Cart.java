package com.fashionstore.model;

import java.time.LocalDateTime;

public class Cart {

    private int cartId;
    private int userId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // Default Constructor
    public Cart() {
    }

    // Parameterized Constructor
    public Cart(int cartId, int userId, LocalDateTime createdDate,
                LocalDateTime updatedDate) {
        this.cartId = cartId;
        this.userId = userId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    // Getters and Setters

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
}