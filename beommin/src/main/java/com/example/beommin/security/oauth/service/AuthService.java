package com.example.beommin.security.oauth.service;

import com.example.beommin.domain.user.repository.UserRepository;
import com.example.beommin.security.CustomUserDetails;
import com.example.beommin.security.jwt.JwtTokenProvider;
import com.example.beommin.security.util.CookieUtil;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@PropertySource("classpath:application.yaml")
public class AuthService {

  @Value("${jwt.cookieKey}")
  private String cookieKey;

  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;

  public AuthService(UserRepository userRepository,
      JwtTokenProvider jwtTokenProvider) {
    this.userRepository = userRepository;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  public String refreshToken(HttpServletRequest request, HttpServletResponse response, String oldAccessToken) {

    // validation refresh token
    String oldRefreshToken = CookieUtil.getCookie(request, cookieKey)
        .map(Cookie::getValue)
        .orElseThrow(RuntimeException::new);

    if(!jwtTokenProvider.validateToken(oldRefreshToken)) {
      throw new RuntimeException("Not validated Refresh Token");
    }

    // Get user info
    Authentication authentication = jwtTokenProvider.getAuthentication(oldAccessToken);
    CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

    Long id = Long.valueOf(user.getName());

    // match refresh token
    String savedRefreshToken = userRepository.getRefreshTokenById(id);

    if(!savedRefreshToken.equals(oldRefreshToken)) throw new RuntimeException("Not matched refresh token");

    // jwt 갱신
    String accessToken = jwtTokenProvider.createAccessToken(authentication);
    jwtTokenProvider.createRefreshToken(authentication, response);

    return accessToken;
  }


}
