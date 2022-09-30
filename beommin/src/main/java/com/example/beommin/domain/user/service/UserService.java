package com.example.beommin.domain.user.service;

import com.example.beommin.domain.user.dto.UserDetailsResponse;
import com.example.beommin.domain.user.entity.User;
import com.example.beommin.domain.user.repository.UserRepository;
import com.example.beommin.domain.user.util.UserConverter;
import java.util.Optional;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User join(OAuth2User oAuth2User, String registrationId) {

    String email = oAuth2User.getAttribute("email");

    return userRepository.findByEmailAndProvider(email, registrationId)
        .orElseThrow(() -> new IllegalArgumentException());
  }

//  public void login(LoginRequest loginRequest) {
//    // TODO : 확인 로직 (로그인 정보가 맞는지)
//    // TODO : 예외 처리 - NotFoundException
//
//    User user = userRepository.findByEmail(loginRequest.getEmail())
//        .orElseThrow(IllegalArgumentException::new);
//
//    // TODO : 비밀번호 틀림 예외 처리
//    if(!user.matchPassword(loginRequest.getPassword())) throw new IllegalArgumentException();
//
//  }

  @Transactional
  public void reIssue() {

  }

  public UserDetailsResponse getUserInfo(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(IllegalArgumentException::new);

    return UserConverter.toUserDetail(user);
  }
}
