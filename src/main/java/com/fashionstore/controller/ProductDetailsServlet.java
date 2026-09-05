package com.fashionstore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductImageDAO;
import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductImageDAOImpl;
import com.fashionstore.dao.impl.ProductVariantDAOImpl;
import com.fashionstore.model.Product;
import com.fashionstore.model.ProductImage;
import com.fashionstore.model.ProductVariant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/product-details")
public class ProductDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;
    private ProductVariantDAO productVariantDAO;
    private ProductImageDAO productImageDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAOImpl();
        productVariantDAO = new ProductVariantDAOImpl();
        productImageDAO = new ProductImageDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productIdParameter = request.getParameter("productId");

        if (productIdParameter == null || productIdParameter.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID is required.");
            return;
        }

        int productId;

        try {
            productId = Integer.parseInt(productIdParameter);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product ID.");
            return;
        }

        Product product = productDAO.getProductById(productId);

        if (product == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Product not found.");
            return;
        }

        List<ProductVariant> variants = productVariantDAO.getVariantsByProductId(productId);
        List<ProductImage> images = productImageDAO.getImagesByProductId(productId);

        List<Product> relatedProducts = productDAO.filterProducts(null, product.getCategoryId(), null, null, null, null, "newest");
        List<Product> filteredRelatedProducts = new ArrayList<>();

        for (Product relatedProduct : relatedProducts) {
            if (relatedProduct.getProductId() != productId) {
                filteredRelatedProducts.add(relatedProduct);
            }

            if (filteredRelatedProducts.size() == 4) {
                break;
            }
        }

        Map<Integer, ProductImage> relatedProductImages = new HashMap<>();

        for (Product relatedProduct : filteredRelatedProducts) {
            List<ProductImage> relatedImages = productImageDAO.getImagesByProductId(relatedProduct.getProductId());

            for (ProductImage relatedImage : relatedImages) {
                if (relatedImage.isPrimary()) {
                    relatedProductImages.put(relatedProduct.getProductId(), relatedImage);
                    break;
                }
            }
        }

        request.setAttribute("product", product);
        request.setAttribute("variants", variants);
        request.setAttribute("images", images);
        request.setAttribute("relatedProducts", filteredRelatedProducts);
        request.setAttribute("relatedProductImages", relatedProductImages);

        request.getRequestDispatcher("/WEB-INF/views/product-details.jsp").forward(request, response);
    }
}