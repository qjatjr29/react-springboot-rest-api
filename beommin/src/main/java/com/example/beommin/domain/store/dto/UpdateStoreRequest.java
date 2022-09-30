package com.example.beommin.domain.store.dto;

import com.example.beommin.common.entity.Address;
import com.example.beommin.common.entity.PhoneNumber;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStoreRequest {

  private String name;

  @NotBlank
  private Address address;

  @Pattern(regexp = "^\\d{2,4}-\\d{3,4}-\\d{4}$")
  private PhoneNumber phoneNumber;

  private String description;

  private String operationHours;

  private String holiday;

  private Long minOrderCost;

  private Long deliveryTip;

  private Long deliveryTime;
}
