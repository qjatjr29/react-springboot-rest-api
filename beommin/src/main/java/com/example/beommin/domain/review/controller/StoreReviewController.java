package com.example.beommin.domain.review.controller;

import com.example.beommin.aop.annotation.CurrentUser;
import com.example.beommin.domain.review.dto.ReviewRequest;
import com.example.beommin.domain.review.dto.ReviewResponse;
import com.example.beommin.domain.review.dto.StoreReviewResponse;
import com.example.beommin.domain.review.dto.UpdateReviewRequest;
import com.example.beommin.domain.review.service.ReviewService;
import com.example.beommin.security.CustomUserDetails;
import java.net.URI;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stores")
public class StoreReviewController {

  private final ReviewService reviewService;

  public StoreReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  @GetMapping("/{storeId}/reviews")
  public ResponseEntity<Slice<ReviewResponse>> getReviewList(@PathVariable Long storeId, @PageableDefault(page = 0, size = 7) Pageable pageable) {
    Slice<ReviewResponse> reviewList = reviewService.findReviewByStore(storeId, pageable);
    return ResponseEntity.ok(reviewList);
  }

  @DeleteMapping("/{storeId}/reviews/{reviewId}")
  public ResponseEntity<Void> delete(
      @CurrentUser CustomUserDetails user,
      @PathVariable Long storeId,
      @PathVariable Long reviewId) {
    reviewService.delete(user.getId(), storeId, reviewId);
    return ResponseEntity.noContent().build();
  }
}
