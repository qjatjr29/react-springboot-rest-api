package com.example.beommin.domain.orders.entity.food.pizza;

import com.example.beommin.domain.orders.entity.food.Food;
import com.example.beommin.domain.orders.entity.food.chicken.ChickenType;
import com.example.beommin.domain.orders.entity.food.chicken.FriedChicken;
import com.example.beommin.domain.orders.entity.food.chicken.HalfChicken;
import com.example.beommin.domain.orders.entity.food.chicken.SpicyChicken;

import java.util.Arrays;
import java.util.UUID;

public enum PizzaType {
    SHRIMP("쉬림프") {
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new ShrimpPizza(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new ShrimpPizza(id, name, price, description, image, storeId);
        }

    },
    CHEESE("치즈") {
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new CheesePizza(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new CheesePizza(id, name, price, description, image, storeId);
        }
    },
    COMBINATION("콤비네이션"){
        @Override
        public Food createFood(UUID id, String name, int price, String description, String image, UUID storeId) {
            if(id == null) {
                return new CombinationPizza(UUID.randomUUID(), name, price, description, image, storeId);
            } else return new CombinationPizza(id, name, price, description, image, storeId);
        }
    };

    private String type;

    PizzaType(String type) {
        this.type = type;
    }

    public static PizzaType getPizzaType(String pizzaType){
        return Arrays.stream(PizzaType.values())
                .filter(pizza -> pizza.getType().equals(pizzaType))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("없는 type 입니다."));
    }

    public abstract Food createFood(UUID id, String name, int price, String description, String image, UUID storeId);
//    public abstract Food createFood(String name, int price, Category category, String description, String image, UUID storeId);

    public String getType() {
        return type;
    }
}
