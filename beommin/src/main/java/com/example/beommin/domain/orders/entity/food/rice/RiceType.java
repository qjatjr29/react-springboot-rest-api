package com.example.beommin.domain.orders.entity.food.rice;

import com.example.beommin.domain.orders.entity.food.Food;
import com.example.beommin.domain.orders.entity.food.chicken.ChickenType;
import com.example.beommin.domain.orders.entity.food.hamburger.ChickenHamburger;
import com.example.beommin.domain.orders.entity.food.hamburger.FrenchFries;
import com.example.beommin.domain.orders.entity.food.hamburger.MeatHamburger;

import java.util.Arrays;
import java.util.UUID;

public enum RiceType {


    PORRIDGE("죽") {
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new Porridge(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new Porridge(id, name, price, description, image, storeId);
        }

    },
    STEW("찌개") {
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new Stew(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new Stew(id, name, price, description, image, storeId);
        }
    },
    FRIED_RICE("복음밥"){
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new FriedRice(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new FriedRice(id, name, price, description, image, storeId);
        }
    };


    private String type;

    RiceType(String type) {
        this.type = type;
    }

    public static RiceType getRiceType(String riceType){
        return Arrays.stream(RiceType.values())
                .filter(rice -> rice.getType().equals(riceType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("없는 type 입니다."));
    }

    public abstract Food createFood(UUID id, String name, int price, String description, String image, UUID storeId);
    public String getType() {
        return type;
    }
}
