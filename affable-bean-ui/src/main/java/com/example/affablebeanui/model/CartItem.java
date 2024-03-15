package com.example.affablebeanui.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartItem {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String categoryName;
    private List<Integer> quantityList = new ArrayList<>();
    private boolean isVisible;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
