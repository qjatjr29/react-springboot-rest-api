package com.example.beommin.domain.orders.entity;

import com.example.beommin.domain.orders.entity.food.Category;

import java.util.Arrays;

public enum OrderStatus {
    ACCEPTED,
    PAYMENT_CONFIRMED,
    READY_FOR_DELIVERY,
    SHIPPED,
    SETTLED,
    CANCELED;

}
