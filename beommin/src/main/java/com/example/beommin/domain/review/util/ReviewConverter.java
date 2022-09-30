package com.example.beommin.domain.review.util;

import com.example.beommin.domain.review.dto.ReviewRequest;
import com.example.beommin.domain.review.entity.Review;
import com.example.beommin.domain.store.entity.Store;

public class ReviewConverter {

  public static Review toReview(ReviewRequest reviewRequest, Long userId, Store store) {

    Review.builder()
        .content(reviewRequest.getContent())
        .image(reviewRequest.getImageUrl())
        .store(store)
        .user(userId)
        .build();

    return null;
  }
}
