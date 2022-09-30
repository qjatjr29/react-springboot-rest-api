package com.example.beommin.domain.orders.entity;

import lombok.Getter;

@Getter
public enum OrderState {
  WAITING_ORDER("주문 대기 중"),
  COOKING("음식 요리 중"),
  WAITING_DELIVERY("배달 대기 중"),
  START_DELIVERY("배달 시작"),
  COMPLETE_DELIVERY("배달 완료"),
  COMPLETE_PACKING("포장 완료"),
  SHOPPING_BASKET("장바구니에 담김"),
  CANCEL_ORDER("주문 취소");


  private String value;

  OrderState(String value) {
    this.value = value;
  }
}
