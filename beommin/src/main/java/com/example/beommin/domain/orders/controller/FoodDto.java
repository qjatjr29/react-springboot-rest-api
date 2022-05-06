package com.example.beommin.domain.orders.controller;

import com.example.beommin.domain.orders.entity.food.Food;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.example.beommin.Utils.createModelMapper;

//@Builder
public class FoodDto {

    private UUID foodId;
    private String name;
    private Integer price;
    private String category;
    private String description;
    private String subCategory;
    private String image;
    private UUID storeId;

    private static final ModelMapper modelMapper = createModelMapper();


    public FoodDto(){}

    public FoodDto(UUID foodId, String name, Integer price, String category, String description, String image, UUID storeId) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
        this.storeId = storeId;
    }

    public FoodDto(UUID foodId, String name, Integer price, String category, String description, String subCategory, String image, UUID storeId) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.subCategory = subCategory;
        this.image = image;
        this.storeId = storeId;
    }

    public static FoodDto of (Food food) {
        return modelMapper.map(food, FoodDto.class);
    }

    public UUID getStoreId() {
        return storeId;
    }

    public String getSubCategory() {
        return subCategory;
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
