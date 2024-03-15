package com.example.affablebeanui.model;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CartBean {
    private Set<CartItem> carts = new HashSet<>();

    public void setCarts(Set<CartItem> carts) {
        this.carts = carts;
    }

    public int cartSize() {
        return carts.size();
    }

    public Set<CartItem> getCartItems() {
        return this.carts;
    }

    public void addToCart(CartItem cartItem) {
        this.carts.add(cartItem);
    }

    public void removeCartItem(int id) {
        this.carts = carts
                .stream()
                .filter(c -> c.getId() != id)
                .collect(Collectors.toSet());
    }

    public void clearCart() {
        carts.clear();
    }
}
