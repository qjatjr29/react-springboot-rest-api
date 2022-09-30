package com.example.beommin.domain.menu.dto;

import com.example.beommin.domain.menu.entity.Category;
import com.example.beommin.domain.menu.entity.MenuState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMenuRequest {

  private String name;

  private Category category;

  private Long price;

  private MenuState state;

  private String description;

}
