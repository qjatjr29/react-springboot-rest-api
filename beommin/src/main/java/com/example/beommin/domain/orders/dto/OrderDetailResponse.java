package com.example.beommin.domain.orders.dto;

import com.example.beommin.domain.orders.entity.OrderState;
import com.example.beommin.domain.orders.entity.OrderType;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailResponse {

  private String userName;

  private String storeName;

  // 배달 / 포장 완료 시간
  private LocalTime EstimatedTime;

  private OrderType orderType;

  private OrderState orderState;

  @Builder.Default
  private List<OrderItemResponse> menuList = new ArrayList<>();

  private Long cost;

  private LocalDateTime createdAt;
}
