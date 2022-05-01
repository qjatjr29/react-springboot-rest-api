package com.example.beommin.domain.orders.controller;

import com.example.beommin.domain.orders.entity.food.Food;
import com.example.beommin.domain.orders.service.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/foods")
public class FoodController {

    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/all")
    public ResponseEntity getAllFoods() {
        List<Food> foods = foodService.getAllFoods();
        List<FoodDto> foodDtos = new ArrayList<>();
        foods.forEach(food -> foodDtos.add(food.toDto()));
        return ResponseEntity.ok()
                .body(foodDtos);
    }

    @GetMapping("/{foodId}")
    public ResponseEntity getFoodById(@PathVariable UUID foodId) {
        Food food = foodService.getFoodById(foodId);
        return ResponseEntity.ok()
                .body(food.toDto());
    }


    @PostMapping("")
    public ResponseEntity insertFood(@RequestBody FoodDto foodDto) {
        Food food = foodService.createFood(foodDto);
        return ResponseEntity.created(URI.create("/foods"))
                .body(food.toDto());
    }

    @PutMapping("")
    public ResponseEntity updateFood(@RequestBody FoodDto foodDto) {
        Food food = foodService.updateFood(foodDto);
        return ResponseEntity.ok(food.toDto());
    }

    @DeleteMapping("{foodId}")
    public ResponseEntity deleteFood(@PathVariable UUID foodId) {
        foodService.deleteFood(foodId);
        return ResponseEntity.noContent().build();
    }

}

