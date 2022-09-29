package com.example.beommin.security;

import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
public class OAuth2Attributes {

  private Map<String, Object> attributes;
  private String provider;
  private String nameAttributeKey;
  private String name;
  private String email;
  private String profileUrl;

  public OAuth2Attributes(Map<String, Object> attributes, String provider,
      String nameAttributeKey, String name, String email, String profileUrl) {
    this.attributes = attributes;
    this.provider = provider;
    this.nameAttributeKey = nameAttributeKey;
    this.name = name;
    this.email = email;
    this.profileUrl = profileUrl;
  }
}
