package com.example.beommin.domain.orders.entity.food.chicken;

import com.example.beommin.domain.orders.controller.FoodDto;
import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FriedChicken implements Food {

    private final UUID foodId;
    private String name;
    private Integer price;
    private String category;
    private String description;
    private String subCategory;
    private String image;
    private UUID storeId;

    public FriedChicken(UUID foodId, String name, Integer price, String description, String image, UUID storeId) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.storeId = storeId;
        category = Category.CHICKEN.getType();
        subCategory = ChickenType.FRIED.getType();
    }


    @Override
    public void changeInfo(String name, int price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> hashMap = new HashMap<>() {{
            put("foodId", foodId.toString().getBytes());
            put("name", name);
            put("category", "치킨");
            put("subCategory", "후라이드");
            put("price", price);
            put("description", description);
            put("image", image);
            put("storeId", storeId.toString().getBytes());
        }};
        return hashMap;
    }

}
