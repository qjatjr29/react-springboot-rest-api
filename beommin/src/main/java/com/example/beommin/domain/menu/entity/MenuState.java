package com.example.beommin.domain.menu.entity;

import lombok.Getter;

@Getter
public enum MenuState {

  ON_SALE("판매 중"),
  NOT_READY("준비가 안됨"),
  OUT_OF_STOCK("품절");

  private String state;

  MenuState(String state) {
    this.state = state;
  }
}
