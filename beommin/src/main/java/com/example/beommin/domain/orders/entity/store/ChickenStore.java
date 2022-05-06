package com.example.beommin.domain.orders.entity.store;

import com.example.beommin.domain.orders.controller.StoreDto;
import com.example.beommin.domain.orders.entity.food.Category;
import com.example.beommin.domain.orders.entity.food.Food;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ChickenStore implements Store{
    private final UUID storeId;
    private String name;
    private String address;
    private String phoneNumber;
    private StoreCategory category;
    private String image;
    private LocalDate createdAt;

    public ChickenStore(UUID storeId, String name, String address, String phoneNumber, StoreCategory category, String image, LocalDate createdAt) {
        this.storeId = storeId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneFormat(phoneNumber);
        this.category = category;
        this.image = image;
        this.createdAt = createdAt;
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber.equals(phoneNumber);
    }

    private String phoneFormat(String number) {
        String regEx = "(\\d{2})(\\d{3,4})(\\d{4})";
        number = number.replaceAll(" ","");
        return number.replaceAll(regEx, "$1-$2-$3");
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> hashMap = new HashMap<>() {{
            put("storeId", storeId.toString().getBytes());
            put("name", name);
            put("address", address);
            put("phoneNumber", phoneNumber);
            put("category", category.getType());
            put("image", image);
            put("createdAt", createdAt);
        }};
        return hashMap;
    }

    @Override
    public void changeInfo(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneFormat(phoneNumber);
    }

    @Override
    public String toString() {
        return "ChickenStore{" +
                "storeId=" + storeId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", category=" + category +
                ", image='" + image + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
