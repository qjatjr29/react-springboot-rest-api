package com.example.beommin.security.oauth.user;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;

public class NaverOAuth2User extends CustomOAuth2User {

  Map<String, Object> response = new HashMap<>();

  public NaverOAuth2User(Map<String, Object> attributes, String nameAttributeKey) {
    super(attributes, nameAttributeKey);
    response = (Map<String, Object>) attributes.get("response");
  }

  @Override
  public String getOAuth2Id() {
    return (String) response.get("id");
  }

  @Override
  public String getEmail() {
    return (String) response.get("email");
  }

  @Override
  public String getName() {
    return (String) response.get("name");
  }

  @Override
  public String getProfileUrl() {
    return (String) response.get("profile_image");
  }

  @Override
  public String getProvider() {
    return "naver";
  }

  @Override
  public String print() {
    return null;
  }

}
