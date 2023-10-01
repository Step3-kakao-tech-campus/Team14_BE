package com.kakaotech.team14backend.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Getter
@Configuration
@ConfigurationProperties(prefix = "redis.cache.ttl")
public class CacheProperties {
  private final Map<String, Long> ttl = new HashMap<>();
}
