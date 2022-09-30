package com.example.beommin.domain.orders.dto;

import com.example.beommin.domain.orders.entity.OrderState;
import com.example.beommin.domain.orders.entity.OrderType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSuccessResponse {

  private String userName;

  private String storeName;

  // 배달 / 포장 완료 시간
  private LocalDate EstimatedTime;

  private OrderType orderType;

  private OrderState orderState;
}
