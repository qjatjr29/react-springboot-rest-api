package com.example.beommin.security.oauth.user;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;


public class KakaoOAuth2User extends CustomOAuth2User {

  Map<String, Object> kakao_account = new HashMap<>();
  Map<String, Object> profile = new HashMap<>();

  public KakaoOAuth2User(Map<String, Object> attributes, String nameAttributeKey) {
    super(attributes, nameAttributeKey);
    kakao_account = (Map<String, Object>) attributes.get("kakao_account");
    profile = (Map<String, Object>) kakao_account.get("profile");
  }

  @Override
  public String getOAuth2Id() {
    return ((Integer) attributes.get("id")).toString();
  }

  @Override
  public String getEmail() {
    return (String) kakao_account.get("email");
  }

  @Override
  public String getName() {
    return (String) profile.get("nickname");
  }

  @Override
  public String getProfileUrl() {
    return (String) profile.get("profile_image_url");
  }

  @Override
  public String getProvider() {
    return "kakao";
  }

  @Override
  public String print() {
    System.out.println("email : " + this.getEmail());
    System.out.println("name : " + this.getName());
    System.out.println("profileUrl : " + this.getProfileUrl());
    System.out.println("provider : " + this.getProvider());
    return null;
  }
}
