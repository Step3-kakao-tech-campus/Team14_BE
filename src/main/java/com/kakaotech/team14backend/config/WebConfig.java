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
        // 메서드 허용
        .allowedMethods(
            HttpMethod.POST.name(), HttpMethod.GET.name(),
            HttpMethod.PUT.name(), HttpMethod.DELETE.name(),
            HttpMethod.OPTIONS.name()
        )
        .allowedHeaders("Authorization")
        .exposedHeaders("Authorization")
        .allowedOrigins("http://localhost:3000", "https://k576830a43f26a.user-app.krampoline.com");
  }
  
}
