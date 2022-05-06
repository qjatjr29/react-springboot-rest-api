package com.example.beommin.domain.orders.controller;

import com.example.beommin.domain.orders.entity.Order;
import com.example.beommin.domain.orders.entity.OrderItem;
import com.example.beommin.domain.orders.entity.food.Category;
import org.modelmapper.ModelMapper;

import java.util.UUID;

import static com.example.beommin.Utils.createModelMapper;

public class OrderItemDto {
    private UUID foodId;
    private String category;
    private Integer price;
    private Integer quantity;

    private static final ModelMapper modelMapper = createModelMapper();

    public OrderItemDto() {
    }

    public OrderItemDto(UUID foodId, String category, Integer price, Integer quantity) {
        this.foodId = foodId;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public static OrderItemDto of (OrderItem orderItem) {
        return modelMapper.map(orderItem, OrderItemDto.class);
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
