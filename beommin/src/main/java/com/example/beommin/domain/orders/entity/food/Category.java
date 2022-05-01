package com.example.beommin.domain.orders.entity.food;

import com.example.beommin.domain.orders.entity.food.chicken.ChickenType;

import java.util.Arrays;
import java.util.UUID;

public enum Category {
    CHICKEN("치킨"){
        @Override
        public Food createFood(String subType, String name, int price, Category category, String description, String image) {
            ChickenType chickenType = ChickenType.getChickenType(subType);
            return chickenType.createFood(name, price, category, description, image);
        }

        @Override
        public Food updateFood(UUID id, String subType, String name, int price, Category category, String description, String image) {
            ChickenType chickenType = ChickenType.getChickenType(subType);
            return chickenType.updateFood(id, name, price, category, description, image);
        }
    },
    PIZZA("피자") {
        @Override
        public Food createFood(String subType, String name, int price, Category category, String description, String image) {
            return null;
        }

        @Override
        public Food updateFood(UUID id, String subType, String name, int price, Category category, String description, String image) {
            return null;
        }
    },
    RICE("밥") {
        @Override
        public Food createFood(String subType, String name, int price, Category category, String description, String image) {
            return null;
        }

        @Override
        public Food updateFood(UUID id, String subType, String name, int price, Category category, String description, String image) {
            return null;
        }
    },
    NOODLE("면") {
        @Override
        public Food createFood(String subType, String name, int price, Category category, String description, String image) {
            return null;
        }

        @Override
        public Food updateFood(UUID id, String subType, String name, int price, Category category, String description, String image) {
            return null;
        }
    },
    HAMBURGER("햄버거") {
        @Override
        public Food createFood(String subType, String name, int price, Category category, String description, String image) {
            return null;
        }

        @Override
        public Food updateFood(UUID id, String subType, String name, int price, Category category, String description, String image) {
            return null;
        }
    },
    DESSERT("디저트") {
        @Override
        public Food createFood(String subType, String name, int price, Category category, String description, String image) {
            return null;
        }

        @Override
        public Food updateFood(UUID id, String subType, String name, int price, Category category, String description, String image) {
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

    public abstract Food createFood(String subType, String name, int price, Category category, String description, String image);
    public abstract Food updateFood(UUID id, String subType, String name, int price, Category category, String description, String image);

    public String getType() {
        return type;
    }
}
