package com.example.beommin.domain.orders.entity.food.noodle;

import com.example.beommin.domain.orders.entity.food.Food;
import com.example.beommin.domain.orders.entity.food.chicken.ChickenType;
import com.example.beommin.domain.orders.entity.food.hamburger.ChickenHamburger;
import com.example.beommin.domain.orders.entity.food.hamburger.FrenchFries;
import com.example.beommin.domain.orders.entity.food.hamburger.MeatHamburger;

import java.util.Arrays;
import java.util.UUID;

public enum NoodleType {

    JJAMBBONG("짬뽕") {
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new Jjambbong(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new Jjambbong(id, name, price, description, image, storeId);
        }

    },
    BLACK_BEAN_SOURCE("짜장면") {
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new BlackBeanSource(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new BlackBeanSource(id, name, price, description, image, storeId);
        }
    },
    COLD_NOODLE("냉면"){
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new ColdNoodle(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new ColdNoodle(id, name, price, description, image, storeId);
        }
    };



    private String type;

    NoodleType(String type) {
        this.type = type;
    }

    public static NoodleType getNoodleType(String noodleType){
        return Arrays.stream(NoodleType.values())
                .filter(noodle -> noodle.getType().equals(noodleType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("없는 type 입니다."));
    }

    public abstract Food createFood(UUID id, String name, int price, String description, String image, UUID storeId);
    public String getType() {
        return type;
    }
}
