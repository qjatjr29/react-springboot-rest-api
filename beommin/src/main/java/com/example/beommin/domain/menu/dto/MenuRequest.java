package com.example.beommin.domain.menu.dto;

import com.example.beommin.common.util.ValidEnum;
import com.example.beommin.domain.menu.entity.Category;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {

  @NotBlank
  private String name;

  @ValidEnum(enumClass = Category.class)
  private Category category;

  private String description;

  private Long price;

}
