package com.kakaotech.team14backend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@EnableCaching
@Configuration
public class RedisConfig {

  private final CacheProperties cacheProperties;

  @Value("${spring.redis.port}")
  public int port;

  @Value("${spring.redis.host}")
  public String host;


  @Bean
  public LettuceConnectionFactory lettuceConnectionFactory(){
    return new LettuceConnectionFactory(new RedisStandaloneConfiguration(host,port));
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(lettuceConnectionFactory());
    redisTemplate.setKeySerializer(new StringRedisSerializer());   // Key: String
    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
    return redisTemplate;
  }

  /**
   * 기본 캐시 설정
   * ttl : 10분
   */

  @Bean
  public RedisCacheConfiguration redisCacheDefaultConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(10))
        .disableCachingNullValues()
        .serializeKeysWith(
            RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
        )
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
        );
  }

  /**
   * 추가 캐시 설정
   * viewCount : 10분
   * post : 15분
   */

  private Map<String, RedisCacheConfiguration> redisCacheConfigurationMap() {
    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
    for (Map.Entry<String, Long> cacheNameAndTimeout : cacheProperties.getTtl().entrySet()) {
      cacheConfigurations
          .put(cacheNameAndTimeout.getKey(), redisCacheDefaultConfiguration().entryTtl(
              Duration.ofSeconds(cacheNameAndTimeout.getValue())));
    }
    return cacheConfigurations;
  }

  @Bean
  public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
    return RedisCacheManager.RedisCacheManagerBuilder
        .fromConnectionFactory(redisConnectionFactory)
        .cacheDefaults(redisCacheDefaultConfiguration())
        .withInitialCacheConfigurations(redisCacheConfigurationMap()).build();
  }



}
