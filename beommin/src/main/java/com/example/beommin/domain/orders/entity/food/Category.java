package com.example.beommin.domain.orders.entity.food;

import com.example.beommin.domain.orders.entity.food.chicken.ChickenType;
import com.example.beommin.domain.orders.entity.food.dessert.DessertType;
import com.example.beommin.domain.orders.entity.food.hamburger.HamburgerType;
import com.example.beommin.domain.orders.entity.food.noodle.NoodleType;
import com.example.beommin.domain.orders.entity.food.pizza.PizzaType;
import com.example.beommin.domain.orders.entity.food.rice.RiceType;

import java.util.Arrays;
import java.util.UUID;

public enum Category {
    CHICKEN("치킨"){
        @Override
        public Food createFood(String subType, String name, int price, String description, String image, UUID storeId) {
            ChickenType chickenType = ChickenType.getChickenType(subType);
            return chickenType.createFood(null, name, price, description, image, storeId);
        }

        @Override
        public Food selectFood(UUID id, String subType, String name, int price, String category, String description, String image, UUID storeId) {
            ChickenType chickenType = ChickenType.getChickenType(subType);
            return chickenType.createFood(id, name, price, description, image, storeId);
        }
    },
    PIZZA("피자") {
        @Override
        public Food createFood(String subType, String name, int price, String description, String image, UUID storeId) {
            PizzaType pizzaType = PizzaType.getPizzaType(subType);
            return pizzaType.createFood(null, name, price, description, image, storeId);
        }

        @Override
        public Food selectFood(UUID id, String subType, String name, int price, String category, String description, String image, UUID storeId) {
            PizzaType pizzaType = PizzaType.getPizzaType(subType);
            return pizzaType.createFood(id, name, price, description, image, storeId);

        }
    },
    RICE("밥") {
        @Override
        public Food createFood(String subType, String name, int price, String description, String image, UUID storeId) {
            RiceType riceType = RiceType.getRiceType(subType);
            return riceType.createFood(null, name, price, description, image, storeId);
        }

        @Override
        public Food selectFood(UUID id, String subType, String name, int price, String category, String description, String image, UUID storeId) {
            RiceType riceType = RiceType.getRiceType(subType);
            return riceType.createFood(id, name, price, description, image, storeId);

        }
    },
    NOODLE("면") {
        @Override
        public Food createFood(String subType, String name, int price, String description, String image, UUID storeId) {
            NoodleType noodleType = NoodleType.getNoodleType(subType);
            return noodleType.createFood(null, name, price, description, image, storeId);
        }

        @Override
        public Food selectFood(UUID id, String subType, String name, int price, String category, String description, String image, UUID storeId) {
            NoodleType noodleType = NoodleType.getNoodleType(subType);
            return noodleType.createFood(id, name, price, description, image, storeId);

        }
    },
    HAMBURGER("햄버거") {
        @Override
        public Food createFood(String subType, String name, int price, String description, String image, UUID storeId) {
            HamburgerType hamburgerType = HamburgerType.getHamburgerType(subType);
            return hamburgerType.createFood(null, name, price, description, image, storeId);
        }

        @Override
        public Food selectFood(UUID id, String subType, String name, int price, String category, String description, String image, UUID storeId) {
            HamburgerType hamburgerType = HamburgerType.getHamburgerType(subType);
            return hamburgerType.createFood(id, name, price, description, image, storeId);

        }
    },
    DESSERT("디저트") {
        @Override
        public Food createFood(String subType, String name, int price, String description, String image, UUID storeId) {
            DessertType dessertType = DessertType.getDessertType(subType);
            return dessertType.createFood(null, name, price, description, image, storeId);
        }

        @Override
        public Food selectFood(UUID id, String subType, String name, int price, String category, String description, String image, UUID storeId) {
            DessertType dessertType = DessertType.getDessertType(subType);
            return dessertType.createFood(id, name, price, description, image, storeId);

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

    public abstract Food createFood(String subType, String name, int price, String description, String image, UUID storeId);
    public abstract Food selectFood(UUID id, String subType, String name, int price, String category, String description, String image, UUID storeId);

    public String getType() {
        return type;
    }
}
