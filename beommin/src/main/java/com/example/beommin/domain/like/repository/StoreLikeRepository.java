package com.example.beommin.domain.like.repository;

import com.example.beommin.domain.like.entity.StoreLike;
import com.example.beommin.domain.store.entity.Store;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreLikeRepository extends JpaRepository<StoreLike, Long> {

  Optional<StoreLike> findByUserIdAndStore(Long userId, Store store);
}
