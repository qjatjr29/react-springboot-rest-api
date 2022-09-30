package com.example.beommin.domain.review.service;

import com.example.beommin.domain.review.dto.ReviewRequest;
import com.example.beommin.domain.review.dto.ReviewResponse;
import com.example.beommin.domain.review.dto.StoreReviewResponse;
import com.example.beommin.domain.review.dto.UpdateReviewRequest;
import com.example.beommin.domain.review.entity.Review;
import com.example.beommin.domain.review.repository.ReviewRepository;
import com.example.beommin.domain.review.util.ReviewConverter;
import com.example.beommin.domain.store.entity.Store;
import com.example.beommin.domain.store.repository.StoreRepository;
import com.example.beommin.domain.store.util.StoreConverter;
import com.example.beommin.domain.user.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final UserRepository userRepository;
  private final StoreRepository storeRepository;

  @Transactional
  public Long save(Long userId, Long storeId, ReviewRequest reviewRequest) {

    Store store = getStore(storeId);

    Review review = ReviewConverter.toReview(reviewRequest, userId, store);

    reviewRepository.save(review);
    return review.getId();
  }

  public Slice<ReviewResponse> findReviewByStore(Long storeId, Pageable pageable) {

    storeRepository.findById(storeId)
        .orElseThrow(IllegalArgumentException::new);

    Slice<ReviewResponse> storeReviews = reviewRepository.findByStore(storeId, pageable);

    return storeReviews;
  }

  public Slice<ReviewResponse> findReviewByUser(Long userId, Pageable pageable) {

    Slice<ReviewResponse> userReviews = reviewRepository.findByUser(userId, pageable);

    return userReviews;
  }

  @Transactional
  public Long update(Long userId, Long storeId, Long reviewId, UpdateReviewRequest updateReviewRequest) {

    Store store = getStore(storeId);

    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(IllegalArgumentException::new);

    if(!review.getStore().equals(store)) throw new IllegalArgumentException();
    if(!review.getUser().equals(userId)) throw new IllegalArgumentException();

    review.changeContent(updateReviewRequest.getContent());
    review.changeImage(updateReviewRequest.getImageUrl());

    reviewRepository.save(review);

    return review.getId();
  }

  @Transactional
  public void delete(Long userId, Long storeId, Long reviewId) {

    Store store = getStore(storeId);

    if(!store.getUser().getId().equals(userId)) throw new IllegalArgumentException();

    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(IllegalArgumentException::new);

    if(!review.getStore().equals(store)) throw new IllegalArgumentException();
    if(!review.getUser().equals(userId)) throw new IllegalArgumentException();

    reviewRepository.delete(review);
  }

  private Store getStore(Long storeId) {
    return storeRepository.findById(storeId)
        .orElseThrow(IllegalArgumentException::new);
  }

}
