package com.example.beommin.domain.orders.entity.store;

import com.example.beommin.domain.orders.controller.StoreDto;
import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Store {
    Map<String,Object> toMap();

    void changeInfo(String name, String address, String phoneNumber);

    StoreDto toDto();
}
