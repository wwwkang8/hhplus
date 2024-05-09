package com.tdd.concert.api.controller.dto.response;

import java.time.LocalDateTime;

import com.tdd.concert.domain.token_redis.model.RedisToken;
import com.tdd.concert.domain.token_redis.status.ProgressStatusR;

public class RedisTokenResponse {

  private long userId;

  private String token;

  private long rank;

  private ProgressStatusR statusR;

  private LocalDateTime expiredAt;

  public RedisTokenResponse() {
  }

  public RedisTokenResponse(long userId, String token, long rank) {
    this.userId = userId;
    this.token = token;
    this.rank = rank;
  }

  public RedisTokenResponse(long userId, String token, long rank,
                       ProgressStatusR statusR) {
    this.userId = userId;
    this.token = token;
    this.rank = rank;
    this.statusR = statusR;
  }

  public RedisTokenResponse(long userId, String token, long rank, ProgressStatusR statusR,
                       LocalDateTime expiredAt) {
    this.userId = userId;
    this.token = token;
    this.rank = rank;
    this.statusR = statusR;
    this.expiredAt = expiredAt;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public long getWaitNo() {
    return waitNo;
  }

  public void setWaitNo(long waitNo) {
    this.waitNo = waitNo;
  }

  public ProgressStatusR getStatus() {
    return statusR;
  }

  public void setStatus(ProgressStatusR statusR) {
    this.statusR = statusR;
  }

  public static TokenResponse from(RedisToken redisToken) {
    return new RedisTokenResponse(
        redisToken.getUser().getUserId(),
        redisToken.getRedisToken(),
        redisToken.getWaitNo(),
        redisToken.getProgressStatusR(),
        redisToken.getExpiredAt()
    );
  }

}
