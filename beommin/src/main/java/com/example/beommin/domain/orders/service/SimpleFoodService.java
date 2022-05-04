package com.example.beommin.domain.orders.service;

import com.example.beommin.domain.orders.controller.FoodDto;
import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;
import com.example.beommin.domain.orders.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SimpleFoodService implements FoodService{

    private final FoodRepository foodRepository;

    public SimpleFoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @Override
    public List<Food> getFoodByCategory(Category category) {
        return foodRepository.findByCategory(category);
    }

    @Override
    public List<FoodDto> getAllFoods() {
        return foodRepository.findAll()
                .stream()
                .map(Food::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public FoodDto createFood(FoodDto foodDto) {
        Category categoryType = Category.getCategoryType(foodDto.getCategory());
        Food food = categoryType.createFood(
                foodDto.getSubCategory(),
                foodDto.getName(),
                foodDto.getPrice(),
                foodDto.getDescription(),
                foodDto.getImage(),
                foodDto.getStoreId());
        return foodRepository.insert(food).toDto();
    }

    @Override
    public Food getFoodById(UUID foodId) {
        return foodRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException());
    }

    @Override
    public List<Food> getFoodByStore(UUID storeId) {
        return foodRepository.findByStore(storeId);
    }

    @Override
    public Food updateFood(FoodDto foodDto) {
        Optional<Food> food = foodRepository.findById(foodDto.getFoodId());
        if(food.isEmpty()) throw new RuntimeException();
        food.get().changeInfo(
                foodDto.getName(),
                foodDto.getPrice(),
                foodDto.getDescription()
        );
        return foodRepository.update(food.get());
    }

    @Override
    public void deleteFood(UUID foodId) {
        Optional<Food> food = foodRepository.findById(foodId);
        food.ifPresent(foodRepository::deleteById);
    }

}
