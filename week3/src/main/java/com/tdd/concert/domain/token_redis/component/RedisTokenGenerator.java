package com.tdd.concert.domain.token_redis.component;

import java.util.Base64;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisTokenGenerator {

  public String generateToken(long userId, long concertId) {

    /** 사용자ID로 토큰 생성 */
    String combinedData = userId + ":" + concertId + ":" + UUID.randomUUID().toString();
    String token = Base64.getUrlEncoder().encodeToString(combinedData.getBytes());

    log.info("[토큰 생성] Base64 토큰 : " + token + ", 사용자ID : " + userId + ", 콘서트ID" + concertId);

    return token;
  }

}
