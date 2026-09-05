package com.fashionstore.dao;

import com.fashionstore.model.CartItem;
import java.util.List;

public interface CartItemDAO {

    boolean addCartItem(CartItem cartItem);

    CartItem getCartItemById(int cartItemId);

    List<CartItem> getCartItemsByCartId(int cartId);
    
    List<CartItem> getCartItemsWithProductDetails(int cartId);

    boolean updateCartItem(CartItem cartItem);

    boolean deleteCartItem(int cartItemId);
}