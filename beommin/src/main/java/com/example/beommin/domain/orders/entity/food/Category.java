package com.example.beommin.domain.orders.entity.food;

import com.example.beommin.domain.orders.entity.food.chicken.ChickenType;

import java.util.Arrays;
import java.util.UUID;

public enum Category {
    CHICKEN("치킨"){
        @Override
        public Food createFood(String subType, String name, int price, Category category, String description, String image, UUID storeId) {
            ChickenType chickenType = ChickenType.getChickenType(subType);
            return chickenType.createFood(name, price, category, description, image, storeId);
        }

        @Override
        public Food updateFood(UUID id, String subType, String name, int price, Category category, String description, String image, UUID storeId) {
            ChickenType chickenType = ChickenType.getChickenType(subType);
            return chickenType.updateFood(id, name, price, category, description, image, storeId);
        }
    },
    PIZZA("피자") {
        @Override
        public Food createFood(String subType, String name, int price, Category category, String description, String image, UUID storeId) {
            return null;
        }

        @Override
        public Food updateFood(UUID id, String subType, String name, int price, Category category, String description, String image, UUID storeId) {
            return null;
        }
    },
    RICE("밥") {
        @Override
        public Food createFood(String subType, String name, int price, Category category, String description, String image, UUID storeId) {
            return null;
        }

        @Override
        public Food updateFood(UUID id, String subType, String name, int price, Category category, String description, String image, UUID storeId) {
            return null;
        }
    },
    NOODLE("면") {
        @Override
        public Food createFood(String subType, String name, int price, Category category, String description, String image, UUID storeId) {
            return null;
        }

        @Override
        public Food updateFood(UUID id, String subType, String name, int price, Category category, String description, String image, UUID storeId) {
            return null;
        }
    },
    HAMBURGER("햄버거") {
        @Override
        public Food createFood(String subType, String name, int price, Category category, String description, String image, UUID storeId) {
            return null;
        }

        @Override
        public Food updateFood(UUID id, String subType, String name, int price, Category category, String description, String image, UUID storeId) {
            return null;
        }
    },
    DESSERT("디저트") {
        @Override
        public Food createFood(String subType, String name, int price, Category category, String description, String image, UUID storeId) {
            return null;
        }

        @Override
        public Food updateFood(UUID id, String subType, String name, int price, Category category, String description, String image, UUID storeId) {
            return null;
        }
    };

    private String type;

    Category(String type) {
        this.type = type;
    }

    public static Category getCategoryType(String category){
        return Arrays.stream(Category.values())
                .filter(c -> c.getType().equals(category))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("없는 type 입니다."));
    }

    public abstract Food createFood(String subType, String name, int price, Category category, String description, String image, UUID storeId);
    public abstract Food updateFood(UUID id, String subType, String name, int price, Category category, String description, String image, UUID storeId);

    public String getType() {
        return type;
    }
}
