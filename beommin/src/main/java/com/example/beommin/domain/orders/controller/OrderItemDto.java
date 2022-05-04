package com.example.beommin.domain.orders.controller;

import com.example.beommin.domain.orders.entity.food.Category;

import java.util.UUID;

public class OrderItemDto {
    private UUID foodId;
    private String category;
    private Integer price;
    private Integer quantity;

    public OrderItemDto() {
    }

    public OrderItemDto(UUID foodId, String category, Integer price, Integer quantity) {
        this.foodId = foodId;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public UUID getFoodId() {
        return foodId;
    }

    public String getCategory() {
        return category;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
