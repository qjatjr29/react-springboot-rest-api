package com.example.beommin.domain.orders.entity.store;

import com.example.beommin.domain.orders.entity.food.Category;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

public enum StoreCategory {
    CHICKEN("치킨") {
        @Override
        public Store createStore(UUID id, String name, String address, String phoneNumber, StoreCategory category, String image, LocalDate createdAt) {
            if(id == null) return new ChickenStore(UUID.randomUUID(), name, address, phoneNumber, category, image, LocalDate.now());
            return new ChickenStore(id, name, address, phoneNumber, category, image, createdAt);
        }
    },
    PIZZA("피자") {
        @Override
        public Store createStore(UUID id, String name, String address, String phoneNumber, StoreCategory category, String image, LocalDate createdAt) {
            if(id == null) return new PizzaStore(UUID.randomUUID(), name, address, phoneNumber, category, image, LocalDate.now());
            return new PizzaStore(id, name, address, phoneNumber, category, image, createdAt);
        }
    },
    RICE("밥") {
        @Override
        public Store createStore(UUID id, String name, String address, String phoneNumber, StoreCategory category, String image, LocalDate createdAt) {
            if(id == null) return new RiceStore(UUID.randomUUID(), name, address, phoneNumber, category, image, LocalDate.now());
            return new RiceStore(id, name, address, phoneNumber, category, image, createdAt);
        }
    },
    NOODLE("면") {
        @Override
        public Store createStore(UUID id, String name, String address, String phoneNumber, StoreCategory category, String image, LocalDate createdAt) {
            if(id == null) return new NoodleStore(UUID.randomUUID(), name, address, phoneNumber, category, image, LocalDate.now());
            return new NoodleStore(id, name, address, phoneNumber, category, image, createdAt);
        }
    },
    HAMBURGER("햄버거") {
        @Override
        public Store createStore(UUID id, String name, String address, String phoneNumber, StoreCategory category, String image, LocalDate createdAt) {
            if(id == null) return new HamburgerStore(UUID.randomUUID(), name, address, phoneNumber, category, image, LocalDate.now());
            return new HamburgerStore(id, name, address, phoneNumber, category, image, createdAt);
        }
    },
    DESSERT("카페") {
        @Override
        public Store createStore(UUID id, String name, String address, String phoneNumber, StoreCategory category, String image, LocalDate createdAt) {
            if(id == null) return new DessertStore(UUID.randomUUID(), name, address, phoneNumber, category, image, LocalDate.now());
            return new DessertStore(id, name, address, phoneNumber, category, image, createdAt);
        }
    };

    private String type;

    StoreCategory(String type) {
        this.type = type;
    }

    public static StoreCategory getCategoryType(String category){
        return Arrays.stream(StoreCategory.values())
                .filter(c -> c.getType().equals(category))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("없는 type 입니다."));
    }
    public abstract Store createStore(UUID id, String name, String address, String phoneNumber, StoreCategory category, String image, LocalDate createdAt);

    public String getType() {
        return type;
    }


}
