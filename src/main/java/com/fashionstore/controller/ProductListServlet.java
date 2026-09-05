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

@WebServlet("/products")
public class ProductListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private ProductImageDAO productImageDAO;

    @Override
    public void init() throws ServletException {
        productDAO = new ProductDAOImpl();
        categoryDAO = new CategoryDAOImpl();
        productImageDAO = new ProductImageDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String gender = request.getParameter("gender");
        String size = request.getParameter("size");
        String minPriceParameter = request.getParameter("minPrice");
        String maxPriceParameter = request.getParameter("maxPrice");
        String categoryParameter = request.getParameter("categoryId");
        String sort = request.getParameter("sort");

        Integer categoryId = null;
        Double minPrice = null;
        Double maxPrice = null;

        try {
            if (categoryParameter != null && !categoryParameter.trim().isEmpty()) {
                categoryId = Integer.parseInt(categoryParameter);
            }
        } catch (NumberFormatException e) {
            categoryId = null;
        }

        try {
            if (minPriceParameter != null && !minPriceParameter.trim().isEmpty()) {
                minPrice = Double.parseDouble(minPriceParameter);
            }
        } catch (NumberFormatException e) {
            minPrice = null;
        }

        try {
            if (maxPriceParameter != null && !maxPriceParameter.trim().isEmpty()) {
                maxPrice = Double.parseDouble(maxPriceParameter);
            }
        } catch (NumberFormatException e) {
            maxPrice = null;
        }

        List<Product> products = productDAO.filterProducts(keyword, categoryId, gender,
        		minPrice, maxPrice, size, sort);
        List<Category> categories = categoryDAO.getAllCategories();
        Map<Integer, ProductImage> primaryImages = new HashMap<>();

        for (Product product : products) {
            List<ProductImage> images = productImageDAO.getImagesByProductId(product.getProductId());
            for (ProductImage image : images) {
                if (image.isPrimary()) {
                    primaryImages.put(product.getProductId(), image);
                    break;
                }
            }
        }

        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.setAttribute("primaryImages", primaryImages);
        request.setAttribute("keyword", keyword);
        request.setAttribute("selectedGender", gender);
        request.setAttribute("selectedSize", size);
        request.setAttribute("selectedSort", sort);
        request.setAttribute("selectedCategoryId", categoryId);
        request.setAttribute("minPrice", minPriceParameter);
        request.setAttribute("maxPrice", maxPriceParameter);

        request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
    }
}