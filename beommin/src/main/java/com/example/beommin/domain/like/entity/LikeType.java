package com.example.beommin.domain.like.entity;


import com.example.beommin.domain.like.dto.LikeResponse;
import com.example.beommin.domain.like.service.LikeService;
import com.example.beommin.domain.like.service.LikeServiceFactory;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

public enum LikeType {

  MENU("menuLikeService") {
    @Override
    public LikeResponse toggle(Long userId, Long menuId) {
      return getLikeService().toggle(userId, menuId);
    }
  },
  STORE("storeLikeService") {
    @Override
    public LikeResponse toggle(Long userId, Long storeId) {
      return getLikeService().toggle(userId, storeId);
    }
  };

  private final String type;
  private LikeService likeService;

  LikeType(String type) {
    this.type = type;
  }

  public String getType() {
    return type;
  }

  public LikeService getLikeService() {
    return likeService;
  }

  public abstract LikeResponse toggle(Long userId, Long targetId);

  public static LikeType of(String target) {
    return LikeType.valueOf(target.toUpperCase());
  }

  @Component
  public static class LikeServiceInjector {

    private final LikeServiceFactory likeServiceFactory;

    public LikeServiceInjector(
        LikeServiceFactory likeServiceFactory) {
      this.likeServiceFactory = likeServiceFactory;
    }

    @PostConstruct
    public void postConstruct() {
      try {
        MENU.likeService = likeServiceFactory.getService(MENU.type);
        STORE.likeService = likeServiceFactory.getService(STORE.type);
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
   }
  }

}
