package com.example.beommin.domain.orders.dto;

import com.example.beommin.domain.orders.entity.OrderState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderState {

  OrderState orderState;


}
