package com.example.beommin.domain.review.repository;

import com.example.beommin.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, FindReviewRepository {


}
