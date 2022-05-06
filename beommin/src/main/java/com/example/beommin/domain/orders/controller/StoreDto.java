package com.example.beommin.domain.orders.controller;

import com.example.beommin.domain.orders.entity.Order;
import com.example.beommin.domain.orders.entity.store.Store;
import com.example.beommin.domain.orders.entity.store.StoreCategory;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.UUID;

import static com.example.beommin.Utils.createModelMapper;

public class StoreDto {
    private UUID storeId;
    private String name;
    private String address;
    private String phoneNumber;
    private String category;
    private String image;
    private LocalDate createdAt;

    private static final ModelMapper modelMapper = createModelMapper();

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

    public static StoreDto of (Store store) {
        return modelMapper.map(store, StoreDto.class);
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
