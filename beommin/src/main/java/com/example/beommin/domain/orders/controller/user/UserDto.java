package com.example.beommin.domain.orders.controller.user;

import java.time.LocalDate;
import java.util.UUID;

public class UserDto {
    private UUID userId;
    private String email;
    private String password;
    private String name;
    private String address;
    private String phoneNumber;
    private LocalDate createdAt;

    public UserDto() {
    }

    public UserDto(UUID userId, String email, String password, String name, String address, String phoneNumber, LocalDate createdAt) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
