package com.example.beommin.domain.user.controller;


import com.example.beommin.aop.annotation.CurrentUser;
import com.example.beommin.domain.user.dto.UserDetailsResponse;
import com.example.beommin.domain.user.service.UserService;
import com.example.beommin.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

//  @PostMapping()
//  public ResponseEntity<SignUpResponse> signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
//    String nickname = userService.signUp(signUpRequest);
//    return ResponseEntity.created(URI.create("")).body(new SignUpResponse(nickname));
//  }
//
//  @PostMapping("/login")
//  public ResponseEntity<Void> login(@RequestBody @Valid LoginRequest loginRequest) {
//    userService.login(loginRequest);
//  }

  @GetMapping()
  public ResponseEntity<UserDetailsResponse> getUserInfo(@CurrentUser CustomUserDetails user) {

    UserDetailsResponse userInfo = userService.getUserInfo(user.getId());

    return ResponseEntity.ok().body(userInfo);
  }


}
