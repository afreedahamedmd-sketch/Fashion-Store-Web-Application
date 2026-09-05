package com.fashionstore.model;

import java.math.BigDecimal;

public class CartItem {
    private int cartItemId;
    private int cartId;
    private int variantId;
    private int quantity;
    private int productId;
    private String productName;
    private String description;
    private BigDecimal price;
    private BigDecimal discount;
    private String gender;
    private String brand;
    private String size;
    private int stock;
    private String imagePath;

    public CartItem() {
    }

    public CartItem(int cartItemId, int cartId, int variantId, int quantity, int productId, String productName, String description, BigDecimal price, BigDecimal discount, String gender, String brand, String size, int stock, String imagePath) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.gender = gender;
        this.brand = brand;
        this.size = size;
        this.stock = stock;
        this.imagePath = imagePath;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}