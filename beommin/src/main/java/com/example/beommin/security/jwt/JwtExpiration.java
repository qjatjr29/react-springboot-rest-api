package com.example.beommin.security.jwt;

public enum JwtExpiration {
  ACCESS_TOKEN_EXPIRATION_TIME("AccessToken 만료 시간 / 30분", 1000L * 60 * 30),
  REFRESH_TOKEN_EXPIRATION_TIME("RefreshToken 만료 시간 / 3일", 1000L * 60 * 60 * 24 * 3),
  LOGIN_EXPIRATION_TIME("로그인 횟수시도 만료 시간 / 6시간 ", 1000L * 60 * 60 * 6),
  BAN_EXPIRATION_TIME("로그인 금지 만료 시간 / 1일", 1000L * 60 * 60 * 24);


  private String description;
  private Long value;

  JwtExpiration(String description, Long value) {
    this.description = description;
    this.value = value;
  }

  public String getDescription() {
    return description;
  }

  public Long getValue() {
    return value;
  }
}
