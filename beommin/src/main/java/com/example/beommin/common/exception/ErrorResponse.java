package com.example.beommin.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse {

  private String message;

  public ErrorResponse(String message) {
    this.message = message;
  }
}
