package com.example.beommin.domain.orders.entity.food.chicken;

import com.example.beommin.domain.orders.controller.FoodDto;
import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpicyChicken implements Food {

    private final UUID foodId;
    private String name;
    private Integer price;
    private Category category;
    private String description;
    private String image;
    private UUID storeId;

    public SpicyChicken(UUID foodId, String name, Integer price, Category category, String description, String image, UUID storeId) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
        this.storeId = storeId;
    }


    @Override
    public void changeInfo(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    @Override
    public FoodDto toDto() {
        return new FoodDto(foodId, name, price, category.getType(), description, "양념", image, storeId);
    }
    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> hashMap = new HashMap<>() {{
            put("foodId", foodId.toString().getBytes());
            put("name", name);
            put("category", category.getType());
            put("subCategory", "양념");
            put("price", price);
            put("description", description);
            put("image", image);
            put("storeId", storeId.toString().getBytes());
        }};
        return hashMap;
    }

    @Override
    public String toString() {
        return "SpicyChicken{" +
                "foodId=" + foodId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category=" + category +
                ", description='" + description + '\'' +
                '}';
    }
}
