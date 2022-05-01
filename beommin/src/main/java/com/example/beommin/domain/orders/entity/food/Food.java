package com.example.beommin.domain.orders.entity.food;

import com.example.beommin.domain.orders.controller.FoodDto;

import java.util.Map;
import java.util.UUID;

public interface Food {

    void changeInfo(String name, int price, String description);

    FoodDto toDto();

    Map<String,Object> toMap();
}
