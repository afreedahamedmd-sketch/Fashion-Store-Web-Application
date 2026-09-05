package com.fashionstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.CheckoutDAO;
import com.fashionstore.dao.UserDAO;

import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;
import com.fashionstore.dao.impl.CheckoutDAOImpl;
import com.fashionstore.dao.impl.UserDAOImpl;

import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Order;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private CartDAO cartDAO;
    private CartItemDAO cartItemDAO;
    private UserDAO userDAO;
    private CheckoutDAO checkoutDAO;

    @Override
    public void init() throws ServletException {

        cartDAO = new CartDAOImpl();
        cartItemDAO = new CartItemDAOImpl();
        userDAO = new UserDAOImpl();
        checkoutDAO = new CheckoutDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        User user = userDAO.getUserById(userId);

        if (user == null) {

            session.invalidate();

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        Cart cart = cartDAO.getCartByUserId(userId);

        if (cart == null) {

            response.sendRedirect(
                    request.getContextPath() + "/cart");

            return;
        }

        List<CartItem> cartItems =
                cartItemDAO.getCartItemsWithProductDetails(
                        cart.getCartId());

        if (cartItems == null || cartItems.isEmpty()) {

            response.sendRedirect(
                    request.getContextPath() + "/cart");

            return;
        }

        BigDecimal subtotal =
                calculateSubtotal(cartItems);

        BigDecimal shipping =
                calculateShipping(subtotal);

        BigDecimal total =
                subtotal.add(shipping);

        request.setAttribute("user", user);
        request.setAttribute("cart", cart);
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("subtotal", subtotal);
        request.setAttribute("shipping", shipping);
        request.setAttribute("total", total);

        request.getRequestDispatcher(
                "/WEB-INF/views/checkout.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        /*
         * Make sure the customer is logged in.
         */
        if (session == null ||
            session.getAttribute("userId") == null) {

            response.sendRedirect(
                    request.getContextPath() + "/login");

            return;
        }

        int userId = (Integer) session.getAttribute("userId");

        /*
         * Get checkout form data.
         */
        String userName =
                request.getParameter("userName");

        String phone =
                request.getParameter("phone");

        String shippingAddress =
                request.getParameter("shippingAddress");

        String city =
                request.getParameter("city");

        String state =
                request.getParameter("state");

        String pincode =
                request.getParameter("pincode");

        String paymentMethod =
                request.getParameter("paymentMethod");

        /*
         * Basic validation.
         */
        if (isBlank(userName) ||
            isBlank(phone) ||
            isBlank(shippingAddress) ||
            isBlank(city) ||
            isBlank(state) ||
            isBlank(pincode) ||
            isBlank(paymentMethod)) {

            request.setAttribute(
                    "errorMessage",
                    "Please fill in all required checkout details.");

            reloadCheckoutPage(request, response, userId);

            return;
        }

        /*
         * Validate payment method against the values
         * allowed by the orders table.
         */
        if (!isValidPaymentMethod(paymentMethod)) {

            request.setAttribute(
                    "errorMessage",
                    "Invalid payment method selected.");

            reloadCheckoutPage(request, response, userId);

            return;
        }

        /*
         * Get the customer's cart again directly
         * from the database.
         */
        Cart cart = cartDAO.getCartByUserId(userId);

        if (cart == null) {

            response.sendRedirect(
                    request.getContextPath() + "/cart");

            return;
        }

        List<CartItem> cartItems =
                cartItemDAO.getCartItemsWithProductDetails(
                        cart.getCartId());

        if (cartItems == null || cartItems.isEmpty()) {

            response.sendRedirect(
                    request.getContextPath() + "/cart");

            return;
        }

        /*
         * Calculate the current cart total.
         *
         * These values are calculated on the server.
         * We do not trust a total sent from the browser.
         */
        BigDecimal subtotal =
                calculateSubtotal(cartItems);

        BigDecimal shipping =
                calculateShipping(subtotal);

        BigDecimal total =
                subtotal.add(shipping);

        /*
         * Create the Order object.
         */
        Order order = new Order();

        order.setUserId(userId);
        order.setTotalAmount(total);
        order.setStatus("Pending");
        order.setPaymentMethod(paymentMethod);
        order.setShippingAddress(shippingAddress.trim());
        order.setCity(city.trim());
        order.setState(state.trim());
        order.setPincode(pincode.trim());

        /*
         * Place the complete order transaction.
         */
        int orderId =
                checkoutDAO.placeOrder(
                        order,
                        cart.getCartId(),
                        subtotal,
                        shipping,
                        total);

        /*
         * Successful order.
         */
        if (orderId > 0) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/order-success?orderId="
                    + orderId);

            return;
        }

        /*
         * Something went wrong.
         */
        request.setAttribute(
                "errorMessage",
                "Unable to place your order. "
                + "Your cart may have changed or a product "
                + "may no longer have enough stock.");

        reloadCheckoutPage(request, response, userId);
    }

    private BigDecimal calculateSubtotal(
            List<CartItem> cartItems) {

        BigDecimal subtotal = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {

            if (cartItem.getPrice() == null) {
                continue;
            }

            BigDecimal itemPrice =
                    cartItem.getPrice();

            BigDecimal itemTotal =
                    itemPrice.multiply(
                            BigDecimal.valueOf(
                                    cartItem.getQuantity()));

            subtotal =
                    subtotal.add(itemTotal);
        }

        return subtotal;
    }

    private BigDecimal calculateShipping(
            BigDecimal subtotal) {

        if (subtotal.compareTo(
                new BigDecimal("2000.00")) >= 0) {

            return BigDecimal.ZERO;
        }

        return new BigDecimal("100.00");
    }

    private boolean isValidPaymentMethod(
            String paymentMethod) {

        return "Cash on Delivery".equals(paymentMethod)
                || "UPI".equals(paymentMethod)
                || "Credit Card".equals(paymentMethod)
                || "Debit Card".equals(paymentMethod)
                || "Net Banking".equals(paymentMethod);
    }

    private boolean isBlank(String value) {

        return value == null ||
               value.trim().isEmpty();
    }

    private void reloadCheckoutPage(
            HttpServletRequest request,
            HttpServletResponse response,
            int userId)
            throws ServletException, IOException {

        User user = userDAO.getUserById(userId);

        Cart cart = cartDAO.getCartByUserId(userId);

        if (cart == null) {

            response.sendRedirect(
                    request.getContextPath() + "/cart");

            return;
        }

        List<CartItem> cartItems =
                cartItemDAO.getCartItemsWithProductDetails(
                        cart.getCartId());

        if (cartItems == null || cartItems.isEmpty()) {

            response.sendRedirect(
                    request.getContextPath() + "/cart");

            return;
        }

        BigDecimal subtotal =
                calculateSubtotal(cartItems);

        BigDecimal shipping =
                calculateShipping(subtotal);

        BigDecimal total =
                subtotal.add(shipping);

        request.setAttribute("user", user);
        request.setAttribute("cart", cart);
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("subtotal", subtotal);
        request.setAttribute("shipping", shipping);
        request.setAttribute("total", total);

        request.getRequestDispatcher(
                "/WEB-INF/views/checkout.jsp")
                .forward(request, response);
    }
}