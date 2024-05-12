package com.tdd.concert.api.controller.dto.response;

import java.time.LocalDateTime;

import com.tdd.concert.domain.token_redis.model.RedisToken;
import com.tdd.concert.domain.token_redis.status.ProgressStatusR;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RedisTokenResponse {

  private long userId;

  private long concertId;

  private String token;

  private long rank;

  private ProgressStatusR statusR;

  private LocalDateTime expiredAt;

  public RedisTokenResponse(long userId, String token,
                            ProgressStatusR statusR, LocalDateTime expiredAt) {
    this.userId = userId;
    this.token = token;
    this.statusR = statusR;
    this.expiredAt = expiredAt;
  }

  public RedisTokenResponse(long concertId, String token, long rank,
                            ProgressStatusR statusR) {
    this.concertId = concertId;
    this.token = token;
    this.rank = rank;
    this.statusR = statusR;
  }

  public RedisTokenResponse(long userId, long concertId, String token, long rank) {
    this.userId = userId;
    this.concertId = concertId;
    this.token = token;
    this.rank = rank;
  }

  public RedisTokenResponse(long userId, long concertId, String token, long rank,
                            ProgressStatusR statusR) {
    this.userId = userId;
    this.concertId = concertId;
    this.token = token;
    this.rank = rank;
    this.statusR = statusR;
  }

  public RedisTokenResponse(long userId, long concertId, String token,
                            ProgressStatusR statusR, LocalDateTime expiredAt) {
    this.userId = userId;
    this.concertId = concertId;
    this.token = token;
    this.statusR = statusR;
    this.expiredAt = expiredAt;
  }

  public static RedisTokenResponse from(RedisToken redisToken) {
    return new RedisTokenResponse(
        redisToken.getUser().getUserId(),
        redisToken.getRedisToken(),
        redisToken.getProgressStatusR(),
        redisToken.getExpiredAt()
    );
  }

}
