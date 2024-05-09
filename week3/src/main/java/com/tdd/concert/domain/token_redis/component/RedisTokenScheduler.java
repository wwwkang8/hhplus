package com.tdd.concert.domain.token_redis.component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.tdd.concert.domain.token_redis.infra.RedisTokenCoreRepositoryImpl;
import com.tdd.concert.domain.token_redis.model.RedisToken;
import com.tdd.concert.domain.token_redis.status.ProgressStatusR;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class RedisTokenScheduler {

  private final RedisTokenCoreRepositoryImpl redisTokenCoreRepositoryImpl;
  private final RedisTemplate<String, String> redisTemplate;
  private final ValueOperations<String, String> valueOperations;
  private final ZSetOperations<String ,String> zSetOperations;

  public RedisTokenScheduler(RedisTemplate<String, String> redisTemplate, RedisTokenCoreRepositoryImpl redisTokenCoreRepositoryImpl) {
    this.redisTokenCoreRepositoryImpl = redisTokenCoreRepositoryImpl;
    this.redisTemplate = redisTemplate;
    this.zSetOperations = redisTemplate.opsForZSet();
    this.valueOperations = redisTemplate.opsForValue();
  }

  /** 여유 자리가 생기면 SortedSet에서 사용자를 끄집어 내서 RedisToken 테이블에 Insert 시켜주는 로직 */
  @Scheduled(cron = "0 * * * * *")
  @Transactional
  public void updateWaitingList() {

  }



  @Scheduled(cron = "0 * * * * *")
  @Transactional
  public void dropToken() {


  }

}
