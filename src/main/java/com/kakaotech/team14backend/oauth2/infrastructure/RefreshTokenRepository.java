package com.kakaotech.team14backend.oauth2.infrastructure;

import com.kakaotech.team14backend.jwt.TokenValidationException;
import com.kakaotech.team14backend.oauth2.domain.RefreshToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
public class RefreshTokenRepository {
  private final RedisTemplate<String,String> redisTemplate;

  public RefreshTokenRepository(@Qualifier("redisTemplateJwt") final RedisTemplate<String,String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public void save(final RefreshToken refreshToken) {
    ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    valueOperations.set(refreshToken.getKakaoId(), refreshToken.getRefreshToken());
    redisTemplate.expire(refreshToken.getRefreshToken(), 60L, TimeUnit.SECONDS);
  }

  public Optional<String> findRTK(String kakaoId) throws TokenValidationException {
    ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
    return Optional.ofNullable(valueOperations.get(kakaoId));
  }
  public String deleteRefreshToken(String kakaoId) throws TokenValidationException{
    ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

    return valueOperations.getAndDelete(kakaoId);
  }
}
