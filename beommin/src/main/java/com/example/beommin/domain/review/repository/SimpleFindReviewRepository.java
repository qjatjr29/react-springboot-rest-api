package com.example.beommin.domain.review.repository;

import static com.example.beommin.domain.review.entity.QReview.review;
import static com.example.beommin.domain.user.entity.QUser.user;

import com.example.beommin.domain.review.dto.ReviewResponse;
import com.example.beommin.domain.review.dto.ReviewUserResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

public class SimpleFindReviewRepository implements FindReviewRepository {

  private final JPAQueryFactory jpaQueryFactory;

  public SimpleFindReviewRepository(EntityManager em) {
    this.jpaQueryFactory = new JPAQueryFactory(em);
  }

  @Override
  public Slice<ReviewResponse> findByStore(Long storeId, Pageable pageable) {
    JPAQuery<ReviewResponse> reviewResponseJPAQuery = jpaQueryFactory.select(
            Projections.constructor(ReviewResponse.class,
                review.id,
                Projections.constructor(ReviewUserResponse.class,
                    user.name,
                    user.profileUrl),
                review.content,
                review.createdAt,
                review.updatedAt
            )
        )
        .from(review)
        .leftJoin(user).on(review.user.eq(user.id))
        .where(review.store.id.eq(storeId))
        .orderBy(review.createdAt.asc());

    return getReviewSliceResponses(reviewResponseJPAQuery, pageable);
  }

  @Override
  public Slice<ReviewResponse> findByUser(Long userId, Pageable pageable) {

    JPAQuery<ReviewResponse> reviewResponseJPAQuery = jpaQueryFactory.select(
            Projections.constructor(ReviewResponse.class,
                review.id,
                review.content,
                review.createdAt,
                review.updatedAt
            )
        )
        .from(review)
        .where(review.user.eq(userId))
        .orderBy(review.createdAt.asc());

    return getReviewSliceResponses(reviewResponseJPAQuery, pageable);
  }

  private SliceImpl<ReviewResponse> getReviewSliceResponses(
      JPAQuery<ReviewResponse> reviewResponseJPAQuery, Pageable pageable) {
    List<ReviewResponse> reviews = reviewResponseJPAQuery.fetch();

    boolean hasNext = false;

    if(reviews.size() > pageable.getPageSize()) {
      reviews.remove(pageable.getPageSize());
      hasNext = true;
    }

    return new SliceImpl<>(reviews, pageable, hasNext);
  }
}
