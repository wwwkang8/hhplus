package com.tdd.concert.domain.token_redis.component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisTokenExtractor {

  public Map extractUserIdAndConcertIdFromToken(String token) {
    // Base64 토큰에서 사용자ID, 콘서트ID 추출
    byte[] decodedBytes = Base64.getUrlDecoder().decode(token);
    String decodedString = new String(decodedBytes);
    String[] parts = decodedString.split(":");

    Map<String, Long> hashMap = new HashMap<>();

    if (parts.length > 0) {
      try {
        hashMap.put("userId", Long.parseLong(parts[0]));
        hashMap.put("concertId", Long.parseLong(parts[1]));
        return hashMap;
      } catch (NumberFormatException e) {
        log.error("Invalid token format: " + token, e);
      }
    }

    throw new IllegalArgumentException("Invalid token format");
  }

}
