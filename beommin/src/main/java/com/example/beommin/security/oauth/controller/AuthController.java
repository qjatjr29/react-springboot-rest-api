package com.example.beommin.security.oauth.controller;

import com.example.beommin.security.oauth.service.AuthService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/refresh")
  public ResponseEntity<String> refreshToken(HttpServletRequest request,
      HttpServletResponse response,
      @RequestBody String accessToken) {
    return ResponseEntity.ok().body(authService.refreshToken(request, response, accessToken));
  }

}
