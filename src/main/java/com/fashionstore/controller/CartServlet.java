package com.fashionstore.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.ProductVariantDAO;
import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;
import com.fashionstore.dao.impl.ProductVariantDAOImpl;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.ProductVariant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private CartDAO cartDAO;
    private CartItemDAO cartItemDAO;
    private ProductVariantDAO productVariantDAO;

    @Override
    public void init() throws ServletException {
        cartDAO = new CartDAOImpl();
        cartItemDAO = new CartItemDAOImpl();
        productVariantDAO = new ProductVariantDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        Cart cart = cartDAO.getCartByUserId(userId);

        if (cart == null) {
            request.setAttribute("cartItems", List.of());
            request.setAttribute("cartTotal", BigDecimal.ZERO);
            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
            return;
        }

        List<CartItem> cartItems = cartItemDAO.getCartItemsWithProductDetails(cart.getCartId());
        BigDecimal cartTotal = calculateCartTotal(cartItems);

        request.setAttribute("cart", cart);
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("cartTotal", cartTotal);

        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        int userId = (Integer) session.getAttribute("userId");
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addToCart(request, userId);
        } else if ("update".equals(action)) {
            updateCartItem(request, userId);
        } else if ("remove".equals(action)) {
            removeCartItem(request, userId);
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    private void addToCart(HttpServletRequest request, int userId) {
        String variantIdParameter = request.getParameter("variantId");
        String quantityParameter = request.getParameter("quantity");

        if (variantIdParameter == null || quantityParameter == null) {
            return;
        }

        try {
            int variantId = Integer.parseInt(variantIdParameter);
            int quantity = Integer.parseInt(quantityParameter);

            if (quantity <= 0) {
                return;
            }

            ProductVariant variant = productVariantDAO.getVariantById(variantId);

            if (variant == null || variant.getStock() <= 0) {
                return;
            }

            if (quantity > variant.getStock()) {
                return;
            }

            Cart cart = cartDAO.getCartByUserId(userId);

            if (cart == null) {
                cart = new Cart();
                cart.setUserId(userId);

                if (!cartDAO.addCart(cart)) {
                    return;
                }

                cart = cartDAO.getCartByUserId(userId);
            }

            if (cart == null) {
                return;
            }

            CartItem existingItem = findCartItem(cart.getCartId(), variantId);

            if (existingItem != null) {
                int requestedQuantity = existingItem.getQuantity() + quantity;

                if (requestedQuantity <= variant.getStock()) {
                    existingItem.setQuantity(requestedQuantity);
                    cartItemDAO.updateCartItem(existingItem);
                }
            } else {
                CartItem cartItem = new CartItem();
                cartItem.setCartId(cart.getCartId());
                cartItem.setVariantId(variantId);
                cartItem.setQuantity(quantity);

                cartItemDAO.addCartItem(cartItem);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void updateCartItem(HttpServletRequest request, int userId) {
        String cartItemIdParameter = request.getParameter("cartItemId");
        String quantityParameter = request.getParameter("quantity");

        if (cartItemIdParameter == null || quantityParameter == null) {
            return;
        }

        try {
            int cartItemId = Integer.parseInt(cartItemIdParameter);
            int quantity = Integer.parseInt(quantityParameter);

            if (quantity <= 0) {
                return;
            }

            Cart cart = cartDAO.getCartByUserId(userId);

            if (cart == null) {
                return;
            }

            List<CartItem> cartItems = cartItemDAO.getCartItemsWithProductDetails(cart.getCartId());

            for (CartItem cartItem : cartItems) {
                if (cartItem.getCartItemId() == cartItemId) {
                    if (quantity <= cartItem.getStock()) {
                        cartItem.setQuantity(quantity);
                        cartItemDAO.updateCartItem(cartItem);
                    }
                    break;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void removeCartItem(HttpServletRequest request, int userId) {
        String cartItemIdParameter = request.getParameter("cartItemId");

        if (cartItemIdParameter == null) {
            return;
        }

        try {
            int cartItemId = Integer.parseInt(cartItemIdParameter);
            Cart cart = cartDAO.getCartByUserId(userId);

            if (cart == null) {
                return;
            }

            List<CartItem> cartItems = cartItemDAO.getCartItemsByCartId(cart.getCartId());

            for (CartItem cartItem : cartItems) {
                if (cartItem.getCartItemId() == cartItemId) {
                    cartItemDAO.deleteCartItem(cartItemId);
                    break;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private CartItem findCartItem(int cartId, int variantId) {
        List<CartItem> cartItems = cartItemDAO.getCartItemsByCartId(cartId);

        for (CartItem cartItem : cartItems) {
            if (cartItem.getVariantId() == variantId) {
                return cartItem;
            }
        }

        return null;
    }

    private BigDecimal calculateCartTotal(List<CartItem> cartItems) {
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cartItems) {
            if (cartItem.getPrice() != null) {
                BigDecimal itemTotal = cartItem.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                total = total.add(itemTotal);
            }
        }

        return total;
    }
}