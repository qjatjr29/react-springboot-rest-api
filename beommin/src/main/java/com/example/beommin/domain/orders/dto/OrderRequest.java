package com.example.beommin.domain.orders.dto;

import com.example.beommin.common.entity.Address;
import com.example.beommin.common.entity.PhoneNumber;
import com.example.beommin.common.util.ValidEnum;
import com.example.beommin.domain.menu.entity.Menu;
import com.example.beommin.domain.orders.entity.OrderType;


import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

  @NotBlank
  private Address address;

  @Pattern(regexp = "^\\d{2,4}-\\d{3,4}-\\d{4}$")
  private PhoneNumber phoneNumber;

  private List<Menu> menuList;

  private Long cost;

  @ValidEnum(enumClass = OrderType.class)
  private OrderType orderType;

}
