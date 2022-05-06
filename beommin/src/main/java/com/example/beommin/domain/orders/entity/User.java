package com.example.beommin.domain.orders.entity;

import com.example.beommin.domain.orders.controller.user.UserDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.beommin.Utils.phoneFormat;

public class User {
    private final UUID userId;
    private final String email;
    private final String password;
    private String name;
    private String address;
    private String phoneNumber;
    private LocalDate createdAt;

    public User(UUID userId, String email, String password, String name, String address, String phoneNumber, LocalDate createdAt) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneFormat(phoneNumber);
        this.createdAt = createdAt;
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        return phoneNumber.equals(phoneNumber);
    }

    public Map<String, Object> toMap() {
        Map<String, Object> hashMap = new HashMap<>() {{
            put("userId", userId.toString().getBytes());
            put("email", email);
            put("password", password);
            put("name", name);
            put("address", address);
            put("phoneNumber", phoneNumber);
            put("createdAt", createdAt);
        }};
        return hashMap;
    }

    public void changeInfo(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneFormat(phoneNumber);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

}
