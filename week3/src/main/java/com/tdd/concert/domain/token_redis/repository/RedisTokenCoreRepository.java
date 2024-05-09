package com.tdd.concert.domain.token_redis.repository;

import java.util.List;

public interface RedisTokenCoreRepository {

  public Long findTokenRank(Long concertId, String token);

  public Long addWaitingQueue(Long concertId, String token);

  public List<String> popTokensFromWaitingQueue(Long concertId, long availableNum);

  public Long currentUsersInWorkingQueue(Long concertId);

  public int addWorkingQueue(Long concertId, List<String> tokenList);

  public void changeTokenStatus(String token);

  public void popFromWorkingQueue(Long concertId, String token);

}
