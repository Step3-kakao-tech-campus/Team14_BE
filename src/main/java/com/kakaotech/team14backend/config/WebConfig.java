package com.kakaotech.team14backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${file.path}")
  private String filePath;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    WebMvcConfigurer.super.addResourceHandlers(registry);

    registry
        .addResourceHandler("/images/**")
        .addResourceLocations("file:" + filePath)
        .setCachePeriod(60 * 60)
        .resourceChain(true)
        .addResolver(new PathResourceResolver());
  }


//  @Override
//  public void addCorsMappings(CorsRegistry registry) {
//    registry.addMapping("/**")
//        .allowedOrigins("*")  // 모든 origin 허용
//        .allowedMethods("*")  // 모든 HTTP 메서드 허용
//        .allowedHeaders("*")  // 모든 헤더 허용
//        .allowCredentials(true)
//        .maxAge(3600);
//
//  }
}
