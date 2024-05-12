package com.tdd.concert.domain.token_redis.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.tdd.concert.domain.token_redis.model.RedisToken;
import com.tdd.concert.domain.token_redis.repository.RedisTokenCoreRepository;
import com.tdd.concert.domain.token_redis.repository.RedisTokenJpaRepository;
import com.tdd.concert.domain.token_redis.status.ProgressStatusR;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RedisTokenCoreRepositoryImpl implements RedisTokenCoreRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private ZSetOperations<String ,String> zSetOperations;
    private final RedisTokenJpaRepository redisTokenJpaRepository;


    private final String WAIT_QUEUE_KEY = "waiting:concert:";
    private final String WORK_QUEUE_KEY = "working:concert:";

    @PostConstruct
    public void init() {
        zSetOperations = redisTemplate.opsForZSet();
    }


    @Override
    public Long findTokenRank(Long concertId, String token) {
        String key = WAIT_QUEUE_KEY + concertId;
        return zSetOperations.rank(key, token);
    }

    @Override
    public Long addWaitingQueue(Long concertId, String token) {
        String key = WAIT_QUEUE_KEY + concertId;

        long score = System.currentTimeMillis();

        zSetOperations.add(key, token, score);
        redisTemplate.expire(key, 3600, TimeUnit.SECONDS);// TTL을 1시간(3600초)으로 설정

        log.info("[WaitingQueue 추가] key : " + key + ", 토큰 : " + token);

        return findTokenRank(concertId, token);
    }

    @Override
    public List<String> popTokensFromWaitingQueue(Long concertId, long availableNum) {
        String key = WAIT_QUEUE_KEY + concertId;

        List<String> tokenList = new ArrayList<>();

         Set<ZSetOperations.TypedTuple<String>> tokenSet = zSetOperations.popMin(key, availableNum);

         for(ZSetOperations.TypedTuple<String> token : tokenSet) {
             tokenList.add(token.getValue());

             log.info("[WaitingQueue 팝] key : " + key + ", 토큰 : " + token);
         }

        return tokenList;
    }

    @Override
    public Long currentUsersInWorkingQueue(Long concertId) {
        String key = WORK_QUEUE_KEY + concertId;
        return zSetOperations.zCard(key);
    }

    @Override
    public List<String> addTokenListWorkingQueue(Long concertId, List<String> tokenList) {
        String key = WORK_QUEUE_KEY + concertId;

        for(String token : tokenList) {
            Long score = System.currentTimeMillis();

            zSetOperations.add(key, token, score);

            log.info("[WorkingQueue 추가] key : " + key + ", 토큰 : " + token);
        }
        redisTemplate.expire(key, 600, TimeUnit.SECONDS);// TTL을 10분(600초)로 설정
        return tokenList;
    }

    @Override
    public String addWorkingQueue(Long concertId, String token) {
        String key = WORK_QUEUE_KEY + concertId;

        Long score = System.currentTimeMillis();

        zSetOperations.add(key, token, score);
        redisTemplate.expire(key, 600, TimeUnit.SECONDS);// TTL을 10분(600초)로 설정

        log.info("[WorkingQueue 추가] key : " + key + ", 토큰 : " + token);

        return token;
    }

    @Override
    public void changeTokenStatus(String token) {
        RedisToken redisToken = redisTokenJpaRepository.findRedisTokenByRedisToken(token);
        redisToken.setProgressStatusR(ProgressStatusR.ONGOING);
    }

    @Override
    public void popFromWorkingQueue(Long concertId, String token) {
        String key = WORK_QUEUE_KEY + concertId;
        zSetOperations.remove(key, token);
    }

}
