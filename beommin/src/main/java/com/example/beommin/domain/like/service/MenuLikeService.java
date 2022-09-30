package com.example.beommin.domain.like.service;

import com.example.beommin.domain.like.dto.LikeResponse;
import com.example.beommin.domain.like.entity.MenuLike;
import com.example.beommin.domain.like.repository.MenuLikeRepository;
import com.example.beommin.domain.menu.entity.Menu;
import com.example.beommin.domain.menu.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MenuLikeService implements LikeService {

  private final MenuRepository menuRepository;
  private final MenuLikeRepository menuLikeRepository;

  public MenuLikeService(
      MenuRepository menuRepository,
      MenuLikeRepository menuLikeRepository) {
    this.menuRepository = menuRepository;
    this.menuLikeRepository = menuLikeRepository;
  }

  @Override
  @Transactional
  public LikeResponse toggle(Long userId, Long menuId) {

    Menu menu = menuRepository.findById(menuId)
        .orElseThrow(IllegalArgumentException::new);

    LikeResponse likeResponse = new LikeResponse();
    likeResponse.setId(menuId);

    menuLikeRepository.findByUserIdAndMenu(userId, menu)
        .ifPresentOrElse(menuLike -> {
          dislike(menuLike);
          likeResponse.setIsLiked(Boolean.FALSE);
        }, () -> {
          like(userId, menu);
          likeResponse.setIsLiked(Boolean.TRUE);
        });

    return likeResponse;
  }

  private void dislike(MenuLike menuLike) {
    menuLike.deleteMenu(menuLike.getMenu());
    menuLikeRepository.delete(menuLike);
  }

  private void like(Long userId, Menu menu) {
    MenuLike menuLike = new MenuLike(userId, menu);
    menuLikeRepository.save(menuLike);
  }

}
