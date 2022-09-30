package com.example.beommin.domain.menu.util;

import com.example.beommin.domain.menu.dto.MenuDetailResponse;
import com.example.beommin.domain.menu.dto.MenuRequest;
import com.example.beommin.domain.menu.dto.MenuSummaryResponse;
import com.example.beommin.domain.menu.entity.Menu;
import com.example.beommin.domain.menu.entity.MenuState;
import com.example.beommin.domain.store.entity.Store;

public class MenuConverter {

  public static Menu toMenu(Store store, MenuRequest menuRequest) {
    return Menu.builder()
        .name(menuRequest.getName())
        .store(store)
        .category(menuRequest.getCategory())
        .price(menuRequest.getPrice())
        .description(menuRequest.getDescription())
        .state(MenuState.ON_SALE)
        .build();
  }

  public static MenuDetailResponse toDetail(Menu menu) {

    return MenuDetailResponse.builder()
        .id(menu.getId())
        .name(menu.getName())
        .storeName(menu.getStore().getName())
        .price(menu.getPrice())
        .category(menu.getCategory().getTitle())
        .state(menu.getState().getState())
        .description(menu.getDescription())
        .createdAt(menu.getCreatedAt())
        .updatedAt(menu.getUpdatedAt())
        .build();
  }

  public static MenuSummaryResponse toMenuList(Menu menu) {

    return MenuSummaryResponse.builder()
        .name(menu.getName())
        .price(menu.getPrice())
        .category(menu.getCategory().getTitle())
        .storeName(menu.getStore().getName())
        .build();
  }
}
