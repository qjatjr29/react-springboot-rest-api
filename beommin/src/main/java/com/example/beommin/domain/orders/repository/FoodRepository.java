package com.example.beommin.domain.orders.repository;

import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FoodRepository {

    Food insert(Food food);

    List<Food> findAll();

    Optional<Food> findById(UUID foodId);

    Food findByName(String name);

    List<Food> findByCategory(Category category);

    Food update(Food food);

    Food deleteById(Food food);

    List<Food> findByStore(UUID storeId);
}
