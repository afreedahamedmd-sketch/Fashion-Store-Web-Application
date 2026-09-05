package com.fashionstore.dao;

import com.fashionstore.model.ProductImage;
import java.util.List;

public interface ProductImageDAO {

    boolean addImage(ProductImage image);

    ProductImage getImageById(int imageId);

    List<ProductImage> getImagesByProductId(int productId);

    boolean updateImage(ProductImage image);

    boolean deleteImage(int imageId);
}