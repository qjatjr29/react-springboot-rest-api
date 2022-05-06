package com.example.beommin.domain.orders.entity.food.chicken;

import com.example.beommin.domain.orders.controller.FoodDto;
import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class SpicyChicken implements Food {

    private final UUID foodId;
    private String name;
    private Integer price;
    private String category;
    private String subCategory;
    private String description;
    private String image;
    private UUID storeId;

    public SpicyChicken(UUID foodId, String name, Integer price, String description, String image, UUID storeId) {
        this.foodId = foodId;
        this.name = name;
        this.price = price;
        this.description = description;
        this.image = image;
        this.storeId = storeId;
        category = Category.CHICKEN.getType();
        subCategory = ChickenType.SPICY.getType();
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
                ", category=" + "치킨" +
                ", description='" + description + '\'' +
                '}';
    }
}
