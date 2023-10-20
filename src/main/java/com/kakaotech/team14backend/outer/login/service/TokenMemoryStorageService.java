package com.kakaotech.team14backend.outer.login.service;


import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenMemoryStorageService {

  private final ConcurrentHashMap<String, String> tokenStorage = new ConcurrentHashMap<>();

  public void saveJwtToken(String sessionId, String jwtToken) {
    tokenStorage.put(sessionId, jwtToken);
  }

  public String getJwtToken(String sessionId) {
    return tokenStorage.get(sessionId);
  }

  public void removeJwtToken(String sessionId) {
    tokenStorage.remove(sessionId);
  }
}
