package com.example.beommin.domain.review.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class StoreReviewResponse {

  private Integer totalCount;

  private List<ReviewResponse> reviewList;
}

