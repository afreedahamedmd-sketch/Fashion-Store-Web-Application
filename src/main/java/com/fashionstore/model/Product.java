package com.fashionstore.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {

    private int productId;
    private int categoryId;
    private String productName;
    private String description;
    private BigDecimal price;
    private BigDecimal discount;
    private String gender;
    private String brand;
    private boolean isAvailable;
    private LocalDateTime createdDate;

    // Default Constructor
    public Product() {
    }

    // Parameterized Constructor
    public Product(int productId, int categoryId, String productName,
                   String description, BigDecimal price, BigDecimal discount,
                   String gender, String brand, boolean isAvailable,
                   LocalDateTime createdDate) {

        this.productId = productId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.gender = gender;
        this.brand = brand;
        this.isAvailable = isAvailable;
        this.createdDate = createdDate;
    }

    // Getters and Setters

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}