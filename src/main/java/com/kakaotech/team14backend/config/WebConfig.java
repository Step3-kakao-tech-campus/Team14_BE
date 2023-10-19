package com.kakaotech.team14backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/image/**")
        .addResourceLocations("file:src/main/resources/image/")
    ;
  }


  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")  // 모든 origin 허용
        .allowedMethods("*")  // 모든 HTTP 메서드 허용
        .allowedHeaders("*")  // 모든 헤더 허용
        .allowCredentials(true)
        .maxAge(3600);

  }
}
