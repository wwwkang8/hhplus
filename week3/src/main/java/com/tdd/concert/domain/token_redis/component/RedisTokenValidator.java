package com.tdd.concert.domain.token_redis.component;

import com.tdd.concert.domain.token_redis.repository.RedisTokenCoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisTokenValidator {

  private final RedisTokenCoreRepository redisTokenCoreRepository;

  public boolean findRedisTokenByConcertId(Long concertId, String redisToken) {
    return redisTokenCoreRepository.findRedisTokenByConcertId(concertId, redisToken) != null;
  }

}
