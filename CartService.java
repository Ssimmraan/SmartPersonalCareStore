package com.personalcare.services;

import com.personalcare.models.CartItem;
import com.personalcare.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartService {
    // Simple singleton cart so controllers share same cart
    private static CartService instance;
    private final List<CartItem> cartItems = new ArrayList<>();

    private CartService() {}

    public static CartService getInstance() {
        if (instance == null) instance = new CartService();
        return instance;
    }

    public void addToCart(Product product, int quantity) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        cartItems.add(new CartItem(product, quantity));
    }

    public void removeFromCart(CartItem item) {
        cartItems.remove(item);
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public double getTotalAmount() {
        return cartItems.stream().mapToDouble(CartItem::getTotalPrice).sum();
    }

    public void clearCart() {
        cartItems.clear();
    }
}
