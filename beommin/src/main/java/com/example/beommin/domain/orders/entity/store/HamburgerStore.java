package com.example.beommin.domain.orders.entity.store;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.beommin.Utils.phoneFormat;

public class HamburgerStore implements Store{
    private final UUID storeId;
    private String name;
    private String address;
    private String phoneNumber;
    private StoreCategory category;
    private String image;
    private LocalDate createdAt;

    public HamburgerStore(UUID storeId, String name, String address, String phoneNumber, StoreCategory category, String image, LocalDate createdAt) {
        this.storeId = storeId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneFormat(phoneNumber);
        this.category = category;
        this.image = image;
        this.createdAt = createdAt;
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
}
