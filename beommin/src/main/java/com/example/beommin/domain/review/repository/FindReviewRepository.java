package com.example.beommin.domain.review.repository;

import com.example.beommin.domain.review.dto.ReviewResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface FindReviewRepository {

  Slice<ReviewResponse> findByStore(Long storeId, Pageable pageable);

  Slice<ReviewResponse> findByUser(Long userId, Pageable pageable);

}
