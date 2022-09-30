package com.example.beommin.domain.like.service;

import com.example.beommin.domain.like.dto.LikeResponse;

public interface LikeService {

  LikeResponse toggle(Long userId, Long id);

}
