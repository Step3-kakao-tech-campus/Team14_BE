package com.kakaotech.team14backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@EnableCaching
@Configuration
public class RedisConfig {

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
    redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));  // Value: 직렬화에 사용할 Object 사용하기
    return redisTemplate;
  }

  /**
   * 기본 캐시 설정
   * ttl : 20분
   */

  @Bean
  public RedisCacheConfiguration redisCacheConfiguration() {
    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(Duration.ofMinutes(20))
        .disableCachingNullValues()
        .serializeKeysWith(
            RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer())
        )
        .serializeValuesWith(
            RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer())
        );
  }





}
