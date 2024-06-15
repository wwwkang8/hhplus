package com.tdd.concert.domain.token_redis.component;

import java.util.List;

public interface RedisTokenManager {

  public Long findTokenRank(Long concertId, String token);

  public Long addWaitingQueue(Long concertId, String token);

  public List<String> popTokensFromWaitingQueue(Long concertId, long availableNum);

  public Long currentUsersInWorkingQueue(Long concertId);

  public List<String> addTokenListWorkingQueue(Long concertId, List<String> tokenList);

  public String addWorkingQueue(Long concertId, String token);

  public void changeTokenStatus(String token);

  public void popFromWorkingQueue(Long concertId, String token);

  public String generateToken(Long userId, Long concertId);

}
