package com.example.beommin.domain.orders.entity;

import com.example.beommin.domain.orders.controller.OrderItemDto;
import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class OrderItem {
    private UUID foodId;
    private Category category;
    private Integer price;
    private Integer quantity;

    public OrderItem() {
    }

    public OrderItem(UUID foodId, Category category, Integer price, Integer quantity) {
        this.foodId = foodId;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "foodId=" + foodId +
                ", category=" + category +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public Map<String, Object> toMap(UUID orderId) {
        Map<String, Object> hashMap = new HashMap<>() {{
            put("orderId", orderId.toString().getBytes());
            put("foodId", foodId.toString().getBytes());
            put("category", category.getType());
            put("price", price);
            put("quantity", quantity);
        }};
        return hashMap;
    }

    public OrderItemDto toDto() {
        return new OrderItemDto(foodId, category.getType(), price, quantity);
    }
}
