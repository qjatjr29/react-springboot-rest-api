package com.example.beommin.security.oauth;

import com.example.beommin.security.util.CookieUtil;
import com.example.beommin.security.oauth.CookieAuthorizationRequestRepository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException exception) throws IOException, ServletException {

    // 로그인 실패시 호출
    // 인증요청시 생성된 쿠키들을 삭제하고 error를 담아 보낸다.

    String url = CookieUtil.getCookie(request, CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
        .map(Cookie::getValue)
        .orElse("/");

    url = UriComponentsBuilder.fromUriString(url)
        .queryParam("error", exception.getLocalizedMessage())
        .build()
        .toUriString();

    cookieAuthorizationRequestRepository.removeAuthorizationRequestCookie(request, response);
    getRedirectStrategy().sendRedirect(request, response, url);
  }
}
