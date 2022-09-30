package com.example.beommin.domain.like.controller;

import com.example.beommin.aop.annotation.CurrentUser;
import com.example.beommin.domain.like.dto.LikeResponse;
import com.example.beommin.domain.like.entity.LikeType;
import com.example.beommin.domain.like.service.LikeService;
import com.example.beommin.domain.like.service.LikeServiceFactory;
import com.example.beommin.domain.like.service.StoreLikeService;
import com.example.beommin.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/likes")
public class LikeController {

  @PutMapping("/{type}/{id}")
  public ResponseEntity<LikeResponse> toggle(
      @CurrentUser CustomUserDetails user,
      @PathVariable String type,
      @PathVariable Long id) {
    LikeResponse likeResponse = LikeType.of(type).toggle(user.getId(), id);
    return ResponseEntity.ok(likeResponse);
  }

}
