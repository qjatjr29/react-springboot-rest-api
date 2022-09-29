package com.example.beommin.domain.user.dto;

import com.example.beommin.domain.user.entity.Role;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserDetailsResponse {

  private Long id;

  private String provider;

  private String email;

  private Role role;

  private String name;

  private String profileUrl;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;
}
