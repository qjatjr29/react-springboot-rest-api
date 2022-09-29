package com.example.beommin.security.oauth.user;

import java.util.Map;

public class GoogleOAuth2User extends CustomOAuth2User{

  public GoogleOAuth2User(Map<String, Object> attributes, String nameAttributeKey) {
    super(attributes, nameAttributeKey);
  }

  @Override
  public String getOAuth2Id() {
    return (String) attributes.get("sub");
  }

  @Override
  public String getEmail() {
    return (String) attributes.get("email");
  }

  @Override
  public String getName() {
    return (String) attributes.get("name");
  }

  @Override
  public String getProfileUrl() {
    return (String) attributes.get("picture");
  }

  @Override
  public String getProvider() {
    return "google";
  }

  @Override
  public String print() {
    return null;
  }

}
