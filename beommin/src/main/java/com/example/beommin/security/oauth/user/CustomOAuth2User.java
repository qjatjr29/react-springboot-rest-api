package com.example.beommin.security.oauth.user;

import java.util.Map;

public abstract class CustomOAuth2User {

  protected Map<String, Object> attributes;

  protected String nameAttributeKey;

  public CustomOAuth2User(Map<String, Object> attributes, String nameAttributeKey) {
    this.attributes = attributes;
    this.nameAttributeKey = nameAttributeKey;
  }

  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public abstract String getOAuth2Id();

  public abstract String getEmail();

  public abstract String getName();

  public abstract String getProfileUrl();

  public abstract String getProvider();

  public abstract String print();

}
