package com.example.beommin.domain.orders.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummaryResponse {

  private LocalDateTime createdAt;

  private Long cost;

  private String storeName;

  private String userName;

  private List<OrderItemResponse> menuList;

}
