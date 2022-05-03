package com.example.beommin.domain.orders.controller;

import com.example.beommin.domain.orders.entity.store.StoreCategory;

import java.time.LocalDate;
import java.util.UUID;

public class StoreDto {
    private UUID storeId;
    private String name;
    private String address;
    private String phoneNumber;
    private String category;
    private String image;
    private LocalDate createdAt;

    public StoreDto() {
    }

    public StoreDto(UUID storeId, String name, String address, String phoneNumber, String category, String image, LocalDate createdAt) {
        this.storeId = storeId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.category = category;
        this.image = image;
        this.createdAt = createdAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public UUID getStoreId() {
        return storeId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }
}
