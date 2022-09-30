package com.example.beommin.domain.store.entity;

import lombok.Getter;

@Getter
public enum StoreState {
  OPEN("엽업 중"),
  CLOSE("영업 종료"),
  HOLIDAY("휴무");


  private String state;

  StoreState(String state) {
    this.state = state;
  }
}
