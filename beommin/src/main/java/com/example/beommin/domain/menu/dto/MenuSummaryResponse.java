package com.example.beommin.domain.menu.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuSummaryResponse {

  private String name;

  private Long price;

  private String category;

  private String storeName;

}
