package com.example.beommin.domain.store.dto;

import com.example.beommin.domain.menu.entity.Category;
import com.example.beommin.domain.store.entity.Sorting;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchStoreRequest {

  private String keyword;

  private List<Category> categoryList;

  private String region;

  @Builder.Default
  private Sorting sorting = Sorting.최신순;
}
