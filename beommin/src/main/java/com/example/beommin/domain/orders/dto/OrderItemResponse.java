package com.example.beommin.domain.orders.dto;

import com.example.beommin.domain.menu.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

  private String menuName;

  private Category category;

  private String description = "";

  private Long price;

}
