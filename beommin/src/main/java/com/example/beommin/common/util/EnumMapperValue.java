package com.example.beommin.common.util;

import lombok.Getter;

@Getter
public class EnumMapperValue {

  private String code;
  private String title;

  public EnumMapperValue(EnumMapperType enumMapperType) {
    code = enumMapperType.getCode();
    title = enumMapperType.getTitle();
  }
}
