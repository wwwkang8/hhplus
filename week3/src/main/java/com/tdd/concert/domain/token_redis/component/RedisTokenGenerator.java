package com.tdd.concert.domain.token_redis.component;

import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisTokenGenerator {

  public String generateToken(long userId) {

    /** 사용자ID로 토큰 생성 */
    String combinedData = userId + "";
    String token = UUID.nameUUIDFromBytes(combinedData.getBytes()).toString();

    log.info("[토큰 생성] UUID 토큰 : " + token + ", 사용자ID : " + userId);

    return token;
  }

}
