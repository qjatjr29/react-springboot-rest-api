package com.example.beommin.domain.orders.entity.food.chicken;

import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Function;

public enum ChickenType {
    FRIED("후라이드") {
        @Override
        public Food updateFood(UUID id, String name, int price, Category category, String description, String image, UUID storeId) {
            return new FriedChicken(id, name, price, category, description, image, storeId);
        }

        @Override
        public Food createFood(String name, int price, Category category, String description, String image, UUID storeId) {
            return new FriedChicken(UUID.randomUUID(), name, price, category, description, image, storeId);
        }
    },
    SPICY("양념") {
        @Override
        public Food updateFood(UUID id, String name, int price, Category category, String description, String image, UUID storeId) {
            return new SpicyChicken(id, name, price, category, description, image, storeId);
        }

        @Override
        public Food createFood(String name, int price, Category category, String description, String image, UUID storeId) {
            return new SpicyChicken(UUID.randomUUID(), name, price, category, description, image, storeId);
        }
    },
    HALF("반반"){
        @Override
        public Food updateFood(UUID id, String name, int price, Category category, String description, String image, UUID storeId) {
            return new HalfChicken(id, name, price, category, description, image, storeId);
        }

        @Override
        public Food createFood(String name, int price, Category category, String description, String image, UUID storeId) {
            return new HalfChicken(UUID.randomUUID(), name, price, category, description, image, storeId);
        }
    };

    private String type;

    ChickenType(String type) {
        this.type = type;
    }

    public static ChickenType getChickenType(String chickenType){
        return Arrays.stream(ChickenType.values())
                .filter(chicken -> chicken.getType().equals(chickenType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("없는 type 입니다."));
    }

    public abstract Food updateFood(UUID id, String name, int price, Category category, String description, String image, UUID storeId);
    public abstract Food createFood(String name, int price, Category category, String description, String image, UUID storeId);

    public String getType() {
        return type;
    }

}
