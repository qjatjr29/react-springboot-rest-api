package com.example.beommin.domain.store.dto;

import com.example.beommin.domain.menu.entity.Category;
import com.example.beommin.common.entity.Address;
import com.example.beommin.common.entity.PhoneNumber;
import com.example.beommin.domain.store.entity.StoreCategory;
import com.example.beommin.domain.store.entity.StoreState;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StoreDetailResponse {

  private String name;

  private Address address;

  private PhoneNumber phoneNumber;

  private String description;

  private StoreState state;

  @Builder.Default
  private List<Category> categoryList = new ArrayList<>();

  private String operationHours;

  private String holiday;

  private Long minOrderCost;

  private Long deliveryTip;

  private Long deliveryTime;

  private Float rating;

}
