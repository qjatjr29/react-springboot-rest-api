package com.example.beommin.security.oauth;

import com.example.beommin.security.jwt.JwtTokenProvider;
import com.example.beommin.security.util.CookieUtil;
import com.example.beommin.domain.user.entity.User;
import com.example.beommin.domain.user.service.UserService;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@PropertySource("classpath:application.yaml")
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends
    SavedRequestAwareAuthenticationSuccessHandler {

  @Value("${app.oauth2.authorizedRedirectUri}")
  private String redirectUri;
  private final UserService userService;
  private final JwtTokenProvider jwtTokenProvider;
  private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws ServletException, IOException {
    if(authentication instanceof OAuth2AuthenticationToken) {

      String redirectUrl = determineUrl(request, response, authentication);

      System.out.println("success handler redirect URL : " + redirectUrl);

      clearAuthenticationAttributes(request, response);
      getRedirectStrategy().sendRedirect(request, response, redirectUrl);

//      OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
//      OAuth2User principal = oauth2Token.getPrincipal();
//      String registrationId = oauth2Token.getAuthorizedClientRegistrationId();
//
//      User user = OAuth2Join(principal, registrationId);
//
//      String token = generateToken(user);
    }
    else {
      super.onAuthenticationSuccess(request, response, authentication);
    }

  }

  protected String determineUrl(HttpServletRequest request, HttpServletResponse response ,Authentication authentication) {

    Optional<String> redirectUrl = CookieUtil.getCookie(request, CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
        .map(Cookie::getValue);

    redirectUrl.ifPresent(url -> {
      if(!isAuthorizedRedirectUri(url)) {
        // TODO : BadRequestException로 변경
        throw new IllegalArgumentException("redirect URIs are not matched");
      }
    });

    String url = redirectUrl.orElse(getDefaultTargetUrl());

    // JWT 생성
    String accessToken = jwtTokenProvider.createAccessToken(authentication);
    jwtTokenProvider.createRefreshToken(authentication, response);

    return UriComponentsBuilder.fromUriString(url)
        .queryParam("accessToken", accessToken)
        .build()
        .toUriString();
//    return url;
  }

  protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
    super.clearAuthenticationAttributes(request);
    cookieAuthorizationRequestRepository.removeAuthorizationRequest(request, response);
  }

  private boolean isAuthorizedRedirectUri(String url) {
    URI clientRedirectUri = URI.create(url);
    URI authorizedUri = URI.create(redirectUri);

    if(authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
        && authorizedUri.getPort() == clientRedirectUri.getPort()) {
      return true;
    }
    else return false;
  }

  private User OAuth2Join(OAuth2User oAuth2User, String registrationId) {
    return userService.join(oAuth2User, registrationId);
  }


  private String generateToken(User user) {
    return jwtTokenProvider.generateAccessToken(user);
  }

}
