package com.example.beommin.domain.orders.entity;

import com.example.beommin.domain.orders.controller.OrderDto;
import com.example.beommin.domain.orders.controller.OrderItemDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.beommin.Utils.phoneFormat;

@Builder
public class OrderList {
    private final UUID orderListId;
    private String name;
    private String phoneNumber;
    private String address;
    private List<OrderItem> orderItems;
    private LocalDateTime updatedAt;

    public OrderList(UUID orderListId, String name, String phoneNumber, String address, List<OrderItem> orderItems, LocalDateTime updatedAt) {
        this.orderListId = orderListId;
        this.name = name;
        this.phoneNumber = phoneFormat(phoneNumber);
        this.address = address;
        this.orderItems = orderItems;
        this.updatedAt = updatedAt;
    }

    public OrderList(UUID orderListId, String name, String phoneNumber, String address, LocalDateTime updatedAt) {
        this.orderListId = orderListId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.updatedAt = updatedAt;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> hashMap = new HashMap<>() {{
            put("orderListId", orderListId.toString().getBytes());
            put("name", name);
            put("phoneNumber", phoneNumber);
            put("address", address);
            put("updatedAt", updatedAt);
        }};
        return hashMap;
    }

}
