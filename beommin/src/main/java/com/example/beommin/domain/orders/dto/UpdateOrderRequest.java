package com.example.beommin.domain.orders.dto;

import com.example.beommin.common.entity.Address;
import com.example.beommin.common.entity.PhoneNumber;
import com.example.beommin.domain.orders.entity.OrderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderRequest {

  private Address address;

  private PhoneNumber phoneNumber;

  private Long cost;

  private OrderType orderType;

}
