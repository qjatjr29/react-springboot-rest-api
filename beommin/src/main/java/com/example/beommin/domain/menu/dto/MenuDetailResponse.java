package com.example.beommin.domain.menu.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuDetailResponse {

  private Long id;

  private String name;

  private String storeName;

  private Long price;

  private String category;

  private String description;

  private String state;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

}
