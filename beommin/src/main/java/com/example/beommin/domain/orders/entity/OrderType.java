package com.example.beommin.domain.orders.entity;

import lombok.Getter;

@Getter
public enum OrderType {
  DELIVERY("배달"),
  PACKING("포장");

  private String value;

  OrderType(String value) {
    this.value = value;
  }
}
