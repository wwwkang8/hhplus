package com.tdd.concert.domain.token_redis.component;

import java.util.List;

import com.tdd.concert.domain.concert.infra.ConcertCoreRepositoryImpl;
import com.tdd.concert.domain.token_redis.infra.RedisTokenCoreRepositoryImpl;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisTokenScheduler {

  private final RedisTokenCoreRepositoryImpl redisTokenCoreRepositoryImpl;
  private final ConcertCoreRepositoryImpl concertCoreRepository;
  private final RedisTemplate<String, String> redisTemplate;
  private ZSetOperations<String ,String> zSetOperations;

  private final long POP_CNT = 750;

  @PostConstruct
  public void init() {
    this.zSetOperations = redisTemplate.opsForZSet();
  }

  /** 여유 자리가 생기면 WaitingQueue에서 끄집어 내서 WorkingQueue에 넣어주는 것 */
  @Scheduled(cron = "*/10 * * * * *")
  @Transactional
  public void updateWaitingQueue() {

    List<Long> concertIdList = concertCoreRepository.concertList();

    for(Long concertId : concertIdList) {

     List<String> tokenList = redisTokenCoreRepositoryImpl.popTokensFromWaitingQueue(concertId, POP_CNT);
     redisTokenCoreRepositoryImpl.addTokenListWorkingQueue(concertId, tokenList);

     for(String token_n : tokenList) {
       redisTokenCoreRepositoryImpl.addWorkingQueue(concertId, token_n);
       log.info("[RedisTokenScheduler] 콘서트ID : "+concertId + ", 토큰 : " + token_n + " WorkingQueue 진입 완료");
     }
    }
  }


}
