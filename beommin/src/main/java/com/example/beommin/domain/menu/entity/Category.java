package com.example.beommin.domain.menu.entity;

import com.example.beommin.common.util.EnumMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Category implements EnumMapperType {

  CHICKEN("치킨"),
  PIZZA("피자"),
  FASTFOOD("패스트푸드"),
  CHINESE("중식"),
  PACKEDFOOD("도시락"),
  MIDNIGHTMEAL("야식"),
  MEAT("고기"),
  WESTERNFOOD("양식"),
  JAPANESEFOOD("일식"),
  KOREANFOOD("한식");

  @Getter
  private final String title;

  @Override
  public String getCode() {
    return name();
  }

}
