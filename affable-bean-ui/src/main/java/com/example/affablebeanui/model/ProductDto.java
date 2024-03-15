package com.example.affablebeanui.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDto {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private String categoryName;
}
