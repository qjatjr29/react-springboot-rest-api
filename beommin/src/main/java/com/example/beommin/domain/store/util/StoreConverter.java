package com.example.beommin.domain.store.util;

import com.example.beommin.domain.review.dto.ReviewResponse;
import com.example.beommin.domain.review.dto.StoreReviewResponse;
import com.example.beommin.domain.store.dto.StoreDetailResponse;
import com.example.beommin.domain.store.dto.StoreRequest;
import com.example.beommin.domain.store.dto.StoreSummaryResponse;
import com.example.beommin.domain.store.entity.Store;
import com.example.beommin.domain.user.entity.User;
import java.util.List;
import java.util.stream.Collectors;

public class StoreConverter {

  public static Store toStore(StoreRequest storeRequest, User user) {
    return Store.builder()
        .user(user)
        .name(storeRequest.getName())
        .description(storeRequest.getDescription())
        .address(storeRequest.getAddress())
        .phoneNumber(storeRequest.getPhoneNumber())
        .holiday(storeRequest.getHoliday())
        .operationHours(storeRequest.getOperationHours())
        .minOrderCost(storeRequest.getMinOrderCost())
        .deliveryTip(storeRequest.getDeliveryTip())
        .deliveryTime(storeRequest.getDeliveryTime())
        .build();
  }

  public static StoreSummaryResponse toStoreList(Store store) {
    return StoreSummaryResponse.builder()
        .name(store.getName())
        .address(store.getAddress())
        .categoryList(List.copyOf(store.getCategoryList().stream()
            .map(s -> s.getCategory()).collect(Collectors.toList())))
        .rating(store.getRating())
        .build();
  }

  public static StoreDetailResponse toDetail(Store store) {
    return StoreDetailResponse.builder()
        .name(store.getName())
        .address(store.getAddress())
        .phoneNumber(store.getPhoneNumber())
        .categoryList(List.copyOf(store.getCategoryList().stream()
            .map(s -> s.getCategory()).collect(Collectors.toList())))
        .state(store.getState())
        .minOrderCost(store.getMinOrderCost())
        .description(store.getDescription())
        .rating(store.getRating())
        .deliveryTip(store.getDeliveryTip())
        .holiday(store.getHoliday())
        .operationHours(store.getOperationHours())
        .deliveryTip(store.getDeliveryTip())
        .deliveryTime(store.getDeliveryTime())
        .build();
  }

  public static StoreReviewResponse toStoreReview(int size, List<ReviewResponse> storeReviews) {
    return StoreReviewResponse.builder()
        .totalCount(size)
        .reviewList(storeReviews)
        .build();
  }
}
