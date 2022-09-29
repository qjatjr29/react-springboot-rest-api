package com.example.beommin.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtException {

  UNKNOWN_ERROR(1, "토큰이 존재하지 않습니다."),
  WRONG_TYPE_TOKEN(2, "변조된 토큰입니다."),
  EXPIRED_TOKEN(3, "만료된 토큰입니다."),
  UNSUPPORTED_TOKEN(4, "변조된 토큰입니다."),
  ACCESS_DENIED(5, "권한이 없습니다.");

  private int code;
  private String message;

}
