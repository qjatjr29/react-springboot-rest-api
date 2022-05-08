package com.example.beommin.domain.orders.entity.food.dessert;

import com.example.beommin.domain.orders.entity.food.Food;


import java.util.Arrays;
import java.util.UUID;

public enum DessertType {

    CAKE("케이크") {
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new Cake(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new Cake(id, name, price, description, image, storeId);
        }

    },
    COFFEE("커피") {
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new Coffee(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new Coffee(id, name, price, description, image, storeId);
        }
    },
    DRINK("음료"){
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new Drink(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new Drink(id, name, price, description, image, storeId);
        }
    };



    private String type;

    DessertType(String type) {
        this.type = type;
    }

    public static DessertType getDessertType(String dessertType){
        return Arrays.stream(DessertType.values())
                .filter(dessert -> dessert.getType().equals(dessertType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("없는 type 입니다."));
    }

    public abstract Food createFood(UUID id, String name, int price, String description, String image, UUID storeId);
    public String getType() {
        return type;
    }
}
