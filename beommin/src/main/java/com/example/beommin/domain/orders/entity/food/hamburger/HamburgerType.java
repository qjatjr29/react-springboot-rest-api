package com.example.beommin.domain.orders.entity.food.hamburger;

import com.example.beommin.domain.orders.entity.food.Food;

import java.util.Arrays;
import java.util.UUID;

public enum HamburgerType {

    CHICKEN("치킨패티") {
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new ChickenHamburger(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new ChickenHamburger(id, name, price, description, image, storeId);
        }

    },
    MEAT("소고기패티") {
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new MeatHamburger(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new MeatHamburger(id, name, price, description, image, storeId);
        }
    },
    FRENCHFRIES("감자튀김"){
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new FrenchFries(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new FrenchFries(id, name, price, description, image, storeId);
        }
    };


    private String type;

    HamburgerType(String type) {
        this.type = type;
    }

    public static HamburgerType getHamburgerType(String hamburgerType){
        return Arrays.stream(HamburgerType.values())
                .filter(hamburger -> hamburger.getType().equals(hamburgerType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("없는 type 입니다."));
    }

    public abstract Food createFood(UUID id, String name, int price, String description, String image, UUID storeId);
    public String getType() {
        return type;
    }
}

