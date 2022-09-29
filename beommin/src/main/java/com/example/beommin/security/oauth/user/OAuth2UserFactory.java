package com.example.beommin.security.oauth.user;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class OAuth2UserFactory {

  public Optional<CustomOAuth2User> createOAuth2User(OAuth2UserRequest userRequest, OAuth2User auth2User) {
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest
        .getClientRegistration()
        .getProviderDetails()
        .getUserInfoEndpoint()
        .getUserNameAttributeName();

    Map<String, Object> attributes = auth2User.getAttributes();
    Collection<? extends GrantedAuthority> authorities = auth2User.getAuthorities();

    switch (registrationId) {
      case "google":
        return Optional.of(new GoogleOAuth2User(attributes, userNameAttributeName));
      case "kakao":
        KakaoOAuth2User kakaoOAuth2User = new KakaoOAuth2User(attributes, userNameAttributeName);
//        System.out.println("=============");
//        System.out.println("kakao : " + kakaoOAuth2User.print());
        return Optional.of(new KakaoOAuth2User(attributes, userNameAttributeName));
      case "naver":
        return Optional.of(new NaverOAuth2User(attributes, userNameAttributeName));
      default:
        return Optional.empty();
    }
  }


}
