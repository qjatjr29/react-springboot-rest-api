package com.example.beommin.domain.store.dto;

import com.example.beommin.domain.menu.entity.Category;
import com.example.beommin.common.entity.Address;
import com.example.beommin.domain.store.entity.StoreCategory;
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
public class StoreSummaryResponse {

  private String name;

  private Address address;

  private Float rating;

  @Builder.Default
  private List<Category> categoryList = new ArrayList<>();

}
