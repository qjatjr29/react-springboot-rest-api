package com.example.beommin.domain.orders.controller;

import com.example.beommin.domain.orders.entity.Order;
import com.example.beommin.domain.orders.entity.OrderItem;
import com.example.beommin.domain.orders.entity.OrderList;
import com.example.beommin.domain.orders.entity.OrderStatus;
import com.example.beommin.domain.orders.entity.food.Food;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.beommin.Utils.createModelMapper;

public class OrderDto {
    private UUID orderId;
    private UUID userId;
    private String name;
    private String phoneNumber;
    private String address;
    private Integer price;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItems;

    private static final ModelMapper modelMapper = createModelMapper();

    public OrderDto() {
    }

    public OrderDto(UUID orderId, UUID userId, String name, String phoneNumber, String address, Integer price, List<OrderItemDto> orderItems, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.price = price;
        this.orderItems = orderItems;
        this.orderStatus = orderStatus;
    }

    public OrderDto(UUID orderId, String name, String phoneNumber, String address) {
        this.orderId = orderId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public OrderDto(String name, String phoneNumber, String address, List<OrderItemDto> orderItems) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.orderItems = orderItems;
    }

    public OrderDto(UUID userId, String name, String phoneNumber, String address, List<OrderItemDto> orderItems) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.orderItems = orderItems;
    }

    public static OrderDto of (Order order) {
        return modelMapper.map(order, OrderDto.class);
    }

    public static OrderDto ofList (OrderList order) {
        return modelMapper.map(order, OrderDto.class);
    }

    public void setOrderItems(List<OrderItemDto> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public UUID getUserId() {
        return userId;
    }

    public Integer getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public List<OrderItemDto> getOrderItems() {
        return orderItems;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + orderId +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", price=" + price +
                ", orderStatus=" + orderStatus +
                ", orderItems=" + orderItems +
                '}';
    }

}
