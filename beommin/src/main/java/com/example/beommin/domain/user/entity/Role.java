package com.example.beommin.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
  GUEST("ROLE_GUEST", "손님"),
  USER("ROLE_USER", "일반 사용자"),
  ADMIN("ROLE_ADMIN", "관리자");

  private final String key;
  private final String title;
}
