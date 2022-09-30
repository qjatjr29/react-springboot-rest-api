package com.example.beommin.domain.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewUserResponse {

  private String name;

  private String profileUrl;

}
