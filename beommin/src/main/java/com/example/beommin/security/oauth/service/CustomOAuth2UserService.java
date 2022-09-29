package com.example.beommin.security.oauth.service;

import com.example.beommin.security.oauth.user.CustomOAuth2User;
import com.example.beommin.security.OAuth2Attributes;
import com.example.beommin.security.oauth.user.OAuth2UserFactory;
import com.example.beommin.security.CustomUserDetails;
import com.example.beommin.domain.user.entity.User;
import com.example.beommin.domain.user.repository.UserRepository;
import com.example.beommin.domain.user.util.UserConverter;
import io.jsonwebtoken.lang.Assert;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

  private final UserRepository userRepository;
  private final OAuth2UserFactory oAuth2UserFactory;

  public CustomOAuth2UserService(
      UserRepository userRepository,
      OAuth2UserFactory oAuth2UserFactory) {
    this.userRepository = userRepository;
    this.oAuth2UserFactory = oAuth2UserFactory;
  }

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    System.out.println("userOauth service");

    Assert.notNull(userRequest, "request가 비어있습니다.");

    OAuth2UserService oAuth2UserService = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

    Map<String, Object> attributes = oAuth2User.getAttributes();

    CustomOAuth2User userInfo = oAuth2UserFactory.createOAuth2User(userRequest, oAuth2User)
        .orElseThrow(() -> new IllegalArgumentException("not found from OAuth2 provider"));
//        OAuth2Attributes userInfo = OAuth2Provider.extract(userRequest, attributes);

    User user = saveOrUpdate(userInfo);

    return CustomUserDetails.create(user, attributes);

  }

  private Map<String, Object> customAttribute(Map<String, Object> attribute, OAuth2Attributes attributes) {
    Map<String, Object> customAttribute = new LinkedHashMap<>();

    customAttribute.put(attributes.getNameAttributeKey(), attribute.get(attributes.getNameAttributeKey()));
    customAttribute.put("provider", attributes.getProvider());
    customAttribute.put("name", attributes.getName());
    customAttribute.put("email", attributes.getEmail());
    customAttribute.put("profileUrl", attributes.getProfileUrl());

    return customAttribute;
  }

  private User saveOrUpdate(CustomOAuth2User userInfo) {
    User user = userRepository.findByEmailAndProvider(userInfo.getEmail(), userInfo.getProvider())
        .map(u -> {
          u.changeProfileUrl(userInfo.getProfileUrl());
          u.changeName(userInfo.getName());
          u.changeEmail(userInfo.getEmail());
          return u;
        })
        .orElse(UserConverter.toUser(userInfo));
    userRepository.save(user);
    return user;
  }
}
