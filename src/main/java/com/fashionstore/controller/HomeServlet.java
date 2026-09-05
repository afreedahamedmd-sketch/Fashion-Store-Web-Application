package com.fashionstore.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductImageDAO;
import com.fashionstore.dao.impl.CategoryDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductImageDAOImpl;
import com.fashionstore.model.Category;
import com.fashionstore.model.Product;
import com.fashionstore.model.ProductImage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CategoryDAO categoryDAO;
    private ProductDAO productDAO;
    private ProductImageDAO productImageDAO;

    @Override
    public void init() throws ServletException {
        categoryDAO = new CategoryDAOImpl();
        productDAO = new ProductDAOImpl();
        productImageDAO = new ProductImageDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryDAO.getAllCategories();
        List<Product> allProducts = productDAO.getAllProducts();
        List<Product> featuredProducts = allProducts.size() > 8 ? allProducts.subList(0, 8) : allProducts;

        Map<Integer, ProductImage> primaryImages = new HashMap<>();
        Map<Integer, ProductImage> categoryImages = new HashMap<>();

        for (Product product : featuredProducts) {
            List<ProductImage> images = productImageDAO.getImagesByProductId(product.getProductId());
            for (ProductImage image : images) {
                if (image.isPrimary()) {
                    primaryImages.put(product.getProductId(), image);
                    break;
                }
            }
        }

        for (Category category : categories) {
        	List<Product> categoryProducts = productDAO.filterProducts(null, category.getCategoryId(), null, null, null, null, "newest");            if (!categoryProducts.isEmpty()) {
                Product categoryProduct = categoryProducts.get(0);
                List<ProductImage> images = productImageDAO.getImagesByProductId(categoryProduct.getProductId());
                for (ProductImage image : images) {
                    if (image.isPrimary()) {
                        categoryImages.put(category.getCategoryId(), image);
                        break;
                    }
                }
            }
        }

        request.setAttribute("categories", categories);
        request.setAttribute("featuredProducts", featuredProducts);
        request.setAttribute("primaryImages", primaryImages);
        request.setAttribute("categoryImages", categoryImages);

        request.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(request, response);
    }
}