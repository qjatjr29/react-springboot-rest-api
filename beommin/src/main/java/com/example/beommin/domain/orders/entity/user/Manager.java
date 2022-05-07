package com.example.beommin.domain.orders.entity.user;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.example.beommin.Utils.phoneFormat;

public class Manager implements Person{

    private final UUID userId;
    private final String email;
    private final String password;
    private String address;
    private String name;
    private String phoneNumber;
    private LocalDate createdAt;

    public Manager(UUID userId, String email, String password, String address, String name, String phoneNumber, LocalDate createdAt) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
    }

    @Override
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

    @Override
    public void changeInfo(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneFormat(phoneNumber);
    }
}
