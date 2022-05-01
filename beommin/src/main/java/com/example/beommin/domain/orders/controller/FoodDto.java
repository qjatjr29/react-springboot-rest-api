package com.example.beommin.domain.orders.controller;

import com.example.beommin.domain.orders.entity.food.Category;

import java.util.UUID;

public class FoodDto {

    private UUID foodId;
    private String name;
    private Integer price;
    private String category;
    private String description;
    private String type;
    private String image;

    public FoodDto(){}

    public FoodDto(UUID foodId, String name, Integer price, String category, String description, String image) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
    }

    public FoodDto(UUID foodId, String name, Integer price, String category, String description, String type, String image) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.type = type;
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public UUID getFoodId() {
        return foodId;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }
}
