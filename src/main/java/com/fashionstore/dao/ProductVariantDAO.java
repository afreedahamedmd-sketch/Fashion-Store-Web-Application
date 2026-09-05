package com.fashionstore.dao;

import com.fashionstore.model.ProductVariant;
import java.util.List;

public interface ProductVariantDAO {

    boolean addVariant(ProductVariant variant);

    ProductVariant getVariantById(int variantId);

    List<ProductVariant> getVariantsByProductId(int productId);

    boolean updateVariant(ProductVariant variant);

    boolean deleteVariant(int variantId);
}