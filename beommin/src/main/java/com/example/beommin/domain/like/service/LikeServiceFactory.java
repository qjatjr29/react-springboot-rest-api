package com.example.beommin.domain.like.service;

import static java.util.Objects.isNull;

import com.example.beommin.common.exception.BadRequestException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class LikeServiceFactory {

  private final Map<String, LikeService> likeServiceMap;

  public LikeService getService(String likeType) {

    LikeService likeService = likeServiceMap.get(likeType);

    if(isNull(likeService)) throw new BadRequestException("");

    return likeService;
  }
}
