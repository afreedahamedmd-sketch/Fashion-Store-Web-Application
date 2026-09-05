package com.fashionstore.model;

public class ProductImage {

    private int imageId;
    private int productId;
    private String imagePath;
    private boolean isPrimary;

    // Default Constructor
    public ProductImage() {
    }

    // Parameterized Constructor
    public ProductImage(int imageId, int productId, String imagePath, boolean isPrimary) {
        this.imageId = imageId;
        this.productId = productId;
        this.imagePath = imagePath;
        this.isPrimary = isPrimary;
    }

    // Getters and Setters

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }
}