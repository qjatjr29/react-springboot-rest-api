package com.example.beommin.domain.user.util;

import com.example.beommin.domain.user.dto.UserDetailsResponse;
import com.example.beommin.security.oauth.user.CustomOAuth2User;
import com.example.beommin.domain.user.entity.Role;
import com.example.beommin.domain.user.entity.User;

public class UserConverter {

  public static User toUser(CustomOAuth2User userInfo) {

    return User.builder()
        .email(userInfo.getEmail())
        .name(userInfo.getName())
        .profileUrl(userInfo.getProfileUrl())
        .provider(userInfo.getProvider())
        .role(Role.USER)
        .build();
  }

  public static UserDetailsResponse toUserDetail(User user) {

    return UserDetailsResponse.builder()
        .id(user.getId())
        .email(user.getEmail())
        .name(user.getName())
        .provider(user.getProvider())
        .profileUrl(user.getProfileUrl())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .role(user.getRole())
        .build();
  }

}
