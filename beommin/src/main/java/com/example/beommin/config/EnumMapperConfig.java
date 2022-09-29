package com.example.beommin.config;

import com.example.beommin.common.util.EnumMapperFactory;
import com.example.beommin.domain.menu.entity.Category;
import java.util.LinkedHashMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumMapperConfig {

  @Bean
  public EnumMapperFactory createEnumMapperFactory() {
    EnumMapperFactory enumMapperFactory = new EnumMapperFactory(new LinkedHashMap<>());

    enumMapperFactory.put("Category", Category.class);

    return enumMapperFactory;
  }


}
