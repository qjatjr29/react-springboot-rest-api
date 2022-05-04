package com.example.beommin.domain.orders.entity;

import com.example.beommin.domain.orders.controller.OrderDto;
import com.example.beommin.domain.orders.controller.OrderItemDto;
import lombok.Builder;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.beommin.Utils.phoneFormat;

@Builder
public class Order {
    private final UUID orderId;
    private String name;
    private String phoneNumber;
    private String address;
    private List<OrderItem> orderItems;
    private Integer price;
    private OrderStatus orderStatus;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Order(UUID orderId, String name, String phoneNumber, String address, List<OrderItem> orderItems, Integer price, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.orderId = orderId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.orderItems = orderItems;
        this.price = price;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Order(UUID orderId, String name, String phoneNumber, String address, Integer price, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.orderId = orderId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.price = price;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.orderItems = new ArrayList<>();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> hashMap = new HashMap<>() {{
            put("orderId", orderId.toString().getBytes());
            put("name", name);
            put("address", address);
            put("phoneNumber", phoneNumber);
            put("orderStatus",  orderStatus.toString());
            put("price", price);
            put("createdAt", createdAt);
            put("updatedAt", updatedAt);
        }};
        return hashMap;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public OrderDto toDto() {
        return new OrderDto(
                orderId,
                name,
                phoneNumber,
                address,
                price,
                orderItems.stream().map(OrderItem::toDto).collect(Collectors.toList()),
                orderStatus
        );
    }
}
