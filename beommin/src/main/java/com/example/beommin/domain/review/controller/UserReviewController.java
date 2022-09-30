package com.example.beommin.domain.review.controller;

import com.example.beommin.aop.annotation.CurrentUser;
import com.example.beommin.domain.review.dto.ReviewRequest;
import com.example.beommin.domain.review.dto.ReviewResponse;
import com.example.beommin.domain.review.dto.UpdateReviewRequest;
import com.example.beommin.domain.review.service.ReviewService;
import com.example.beommin.security.CustomUserDetails;
import java.net.URI;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserReviewController {

  private final ReviewService reviewService;

  public UserReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @PostMapping("/{storeId}/reviews")
  public ResponseEntity<Long> createReview(
      @CurrentUser CustomUserDetails user,
      @PathVariable Long storeId,
      @RequestBody ReviewRequest reviewRequest) {
    Long reviewId = reviewService.save(user.getId(), storeId, reviewRequest);
    return ResponseEntity.created(URI.create("/" + reviewId)).body(reviewId);
  }

  @GetMapping("/reviews")
  public ResponseEntity<Slice<ReviewResponse>> getReviewByUser(@CurrentUser CustomUserDetails user, @PageableDefault(page = 0, size = 7) Pageable pageable) {

    Slice<ReviewResponse> reviewList = reviewService.findReviewByUser(user.getId(), pageable);

    return ResponseEntity.ok(reviewList);
  }
  @PutMapping("/{storeId}/reviews/{reviewId}")
  public ResponseEntity<Long> modify(
      @CurrentUser CustomUserDetails user,
      @PathVariable Long storeId,
      @PathVariable Long reviewId,
      @RequestBody UpdateReviewRequest updateReviewRequest) {

    Long id = reviewService.update(user.getId(), storeId, reviewId, updateReviewRequest);
    return ResponseEntity.ok(id);
  }
}
