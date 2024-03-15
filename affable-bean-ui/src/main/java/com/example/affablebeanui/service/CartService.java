package com.example.affablebeanui.service;

import com.example.affablebeanui.model.CartBean;
import com.example.affablebeanui.model.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartBean cartBean;

    public int cartSize() {
        return cartBean.cartSize();
    }

    public Set<CartItem> cartItems() {
        return cartBean.getCartItems();
    }

    public void addToCart(CartItem cartItem) {
        this.cartBean.addToCart(cartItem);
    }

    public void removeCartItem(int id) {
        cartBean.removeCartItem(id);
    }

    public void setCartItems(Set<CartItem> cartItems) {
        cartBean.setCarts(cartItems);
    }

    public void clearCart() {
        cartBean.clearCart();
    }
}
