package com.example.beommin.domain.like.service;

import com.example.beommin.domain.like.dto.LikeResponse;
import com.example.beommin.domain.like.entity.StoreLike;
import com.example.beommin.domain.like.repository.StoreLikeRepository;
import com.example.beommin.domain.store.entity.Store;
import com.example.beommin.domain.store.repository.StoreRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class StoreLikeService implements LikeService {

  private final StoreRepository storeRepository;
  private final StoreLikeRepository storeLikeRepository;

  public StoreLikeService(
      StoreRepository storeRepository,
      StoreLikeRepository storeLikeRepository) {
    this.storeRepository = storeRepository;
    this.storeLikeRepository = storeLikeRepository;
  }

  @Override
  @Transactional
  public LikeResponse toggle(Long userId, Long storeId) {
    Store store = storeRepository.findById(storeId)
        .orElseThrow(IllegalArgumentException::new);

    LikeResponse likeResponse = new LikeResponse();
    likeResponse.setId(storeId);

    storeLikeRepository.findByUserIdAndStore(userId, store)
        .ifPresentOrElse(storeLike -> {
          dislike(storeLike);
          likeResponse.setIsLiked(Boolean.FALSE);
        }, () -> {
          like(userId, store);
          likeResponse.setIsLiked(Boolean.TRUE);
        });

    return likeResponse;
  }

  private void dislike(StoreLike storeLike) {
    storeLike.deleteStore(storeLike.getStore());
    storeLikeRepository.delete(storeLike);
  }

  private void like(Long userId, Store store) {
    StoreLike storeLike = new StoreLike(userId, store);
    storeLikeRepository.save(storeLike);
  }

}
