package com.example.beommin.domain.orders.service;

import com.example.beommin.domain.orders.controller.FoodDto;
import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;

import java.util.List;
import java.util.UUID;

public interface FoodService {

    List<Food> getFoodByCategory(Category category);

    List<Food> getAllFoods();

    Food createFood(FoodDto foodDto);

    Food getFoodById(UUID foodId);

    List<Food> getFoodByStore(UUID storeId);

    Food updateFood(FoodDto foodDto);

    void deleteFood(UUID foodId);

}
