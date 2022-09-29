package com.example.beommin.security;

import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

public enum OAuth2Provider {

  GOOGLE("google", (attributes, nameAttributeName) -> OAuth2Attributes
      .builder()
      .attributes(attributes)
      .nameAttributeKey(nameAttributeName)
      .provider("google")
      .email((String) attributes.get("email"))
      .name((String) attributes.get("name"))
      .profileUrl((String) attributes.get("profile"))
      .build()),
  NAVER("naver", (attributes, nameAttributeName) -> {
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");
    return OAuth2Attributes
        .builder()
        .attributes(attributes)
        .nameAttributeKey(nameAttributeName)
        .provider("naver")
        .email((String) response.get("email"))
        .name((String) response.get("name"))
        .profileUrl((String) response.get("profile_image"))
        .build();
  }),
  KAKAO("kakao", (attributes, nameAttributeName) -> {
    Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("kakao_account");
    Map<String, Object> profile = (Map<String, Object>) kakao_account.get("profile");
    return OAuth2Attributes
        .builder()
        .attributes(attributes)
        .nameAttributeKey(nameAttributeName)
        .provider("kakao")
        .email((String) profile.get("email"))
        .name((String) profile.get("nickname"))
        .profileUrl((String) profile.get("profile_image_url"))
        .build();
  });


  private final String registrationId;
  private final BiFunction<Map<String, Object>, String, OAuth2Attributes> of;

  OAuth2Provider(String registrationId,
      BiFunction<Map<String, Object>, String, OAuth2Attributes> of) {
    this.registrationId = registrationId;
    this.of = of;
  }

  public static OAuth2Attributes extract(OAuth2UserRequest userRequest, Map<String, Object> attributes) {
    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails()
        .getUserInfoEndpoint()
        .getUserNameAttributeName();

    return Arrays.stream(values())
        .filter(provider -> registrationId.equals(provider.registrationId))
        .findFirst()
        .orElseThrow(IllegalArgumentException::new)
        .of.apply(attributes, userNameAttributeName);
  }

}
