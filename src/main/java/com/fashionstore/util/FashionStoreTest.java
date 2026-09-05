package com.fashionstore.util;

import java.util.List;

import com.fashionstore.dao.OrderDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.OrderDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Order;
import com.fashionstore.model.Product;

public class FashionStoreTest {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("       FASHION STORE DAO TEST");
        System.out.println("========================================");

        ProductDAO productDAO = new ProductDAOImpl();
        OrderDAO orderDAO = new OrderDAOImpl();

        // ============================================
        // 1. CATEGORY FILTER
        // T-Shirts = categoryId 1
        // ============================================

        System.out.println("\n--- CATEGORY FILTER: T-Shirts ---");

        List<Product> categoryProducts =
                productDAO.filterProducts(
                        null,
                        1,
                        null,
                        null,
                        null,
                        null,
                        null
                );

        printProducts(categoryProducts);


        // ============================================
        // 2. GENDER FILTER
        // Women
        // ============================================

        System.out.println("\n--- GENDER FILTER: Women ---");

        List<Product> womenProducts =
                productDAO.filterProducts(
                        null,
                        null,
                        "Women",
                        null,
                        null,
                        null,
                        null
                );

        printProducts(womenProducts);


        // ============================================
        // 3. KEYWORD SEARCH
        // Jeans
        // ============================================

        System.out.println("\n--- SEARCH FILTER: Jeans ---");

        List<Product> jeansProducts =
                productDAO.filterProducts(
                        "Jeans",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                );

        printProducts(jeansProducts);


        // ============================================
        // 4. MINIMUM PRICE
        // ₹1500
        // ============================================

        System.out.println("\n--- MIN PRICE FILTER: ₹1500 ---");

        List<Product> minPriceProducts =
                productDAO.filterProducts(
                        null,
                        null,
                        null,
                        1500.0,
                        null,
                        null,
                        null
                );

        printProducts(minPriceProducts);


        // ============================================
        // 5. MAXIMUM PRICE
        // ₹1000
        // ============================================

        System.out.println("\n--- MAX PRICE FILTER: ₹1000 ---");

        List<Product> maxPriceProducts =
                productDAO.filterProducts(
                        null,
                        null,
                        null,
                        null,
                        1000.0,
                        null,
                        null
                );

        printProducts(maxPriceProducts);


        // ============================================
        // 6. PRICE RANGE
        // ₹1000 - ₹2000
        // ============================================

        System.out.println("\n--- PRICE RANGE: ₹1000 - ₹2000 ---");

        List<Product> priceRangeProducts =
                productDAO.filterProducts(
                        null,
                        null,
                        null,
                        1000.0,
                        2000.0,
                        null,
                        null
                );

        printProducts(priceRangeProducts);


        // ============================================
        // 7. SIZE FILTER
        // Size M
        // ============================================

        System.out.println("\n--- SIZE FILTER: M ---");

        List<Product> sizeProducts =
                productDAO.filterProducts(
                        null,
                        null,
                        null,
                        null,
                        null,
                        "M",
                        null
                );

        printProducts(sizeProducts);


        // ============================================
        // 8. COMBINED FILTER
        // Women + ₹1000-₹2500 + M
        // ============================================

        System.out.println(
                "\n--- COMBINED FILTER: Women + ₹1000-₹2500 + M ---"
        );

        List<Product> combinedProducts =
                productDAO.filterProducts(
                        null,
                        null,
                        "Women",
                        1000.0,
                        2500.0,
                        "M",
                        null
                );

        printProducts(combinedProducts);


        // ============================================
        // 9. USER ORDERS
        // User ID = 1
        // ============================================

        System.out.println(
                "\n--- ORDERS FOR USER 1 ---"
        );

        List<Order> orders =
                orderDAO.getOrdersByUserId(1);

        if (orders == null || orders.isEmpty()) {

            System.out.println("No orders found for User 1.");

        } else {

            for (Order order : orders) {

                System.out.println(
                        "Order ID: " + order.getOrderId()
                        + " | Amount: ₹" + order.getTotalAmount()
                        + " | Status: " + order.getStatus()
                        + " | Payment: " + order.getPaymentMethod()
                );
            }
        }


        // ============================================
        // TEST COMPLETE
        // ============================================

        System.out.println("\n========================================");
        System.out.println("       FASHION STORE TEST COMPLETE");
        System.out.println("========================================");
    }


    // ============================================
    // PRINT PRODUCTS
    // ============================================

    private static void printProducts(List<Product> products) {

        if (products == null || products.isEmpty()) {

            System.out.println("No products found.");
            return;
        }

        for (Product product : products) {

            System.out.println(
                    "Product ID: " + product.getProductId()
                    + " | Name: " + product.getProductName()
                    + " | Brand: " + product.getBrand()
                    + " | Gender: " + product.getGender()
                    + " | Price: ₹" + product.getPrice()
            );
        }

        System.out.println(
                "Total Products Found: " + products.size()
        );
    }
}