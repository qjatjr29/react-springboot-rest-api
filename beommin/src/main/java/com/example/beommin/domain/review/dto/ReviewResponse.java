package com.example.beommin.domain.review.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class ReviewResponse {

  private Long id;

  private ReviewUserResponse user;

  private String comment;

  private LocalDate createdAt;

  private LocalDate updatedAt;

  public ReviewResponse(Long id, ReviewUserResponse user, String comment,
      LocalDate createdAt, LocalDate updatedAt) {
    this.id = id;
    this.user = user;
    this.comment = comment;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public ReviewResponse(Long id, String comment, LocalDate createdAt, LocalDate updatedAt) {
    this.id = id;
    this.comment = comment;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
