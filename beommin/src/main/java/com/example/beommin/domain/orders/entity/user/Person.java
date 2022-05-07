package com.example.beommin.domain.orders.entity.user;

import java.util.Map;

public interface Person {

    Map<String, Object> toMap();

    void changeInfo(String name, String address, String phoneNumber);

}
