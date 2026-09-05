package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Product;

public interface ProductDAO {
    boolean addProduct(Product product);
    Product getProductById(int productId);
    List<Product> getAllProducts();
    List<Product> filterProducts(String keyword, Integer categoryId, String gender, Double minPrice, Double maxPrice, String size, String sort);
    boolean updateProduct(Product product);
    boolean deleteProduct(int productId);
}