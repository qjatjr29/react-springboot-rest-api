package com.example.beommin.domain.store.dto;

import com.example.beommin.common.entity.Address;
import com.example.beommin.common.entity.PhoneNumber;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreRequest {

  @NotBlank
  private String name;

  private Address address;

  private PhoneNumber phoneNumber;

  private String description;

  private String operationHours;

  private String holiday;

  private Long minOrderCost;

  private Long deliveryTip;

  private Long deliveryTime;
}
