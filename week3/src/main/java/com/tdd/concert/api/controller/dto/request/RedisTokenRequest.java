package com.tdd.concert.api.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RedisTokenRequest {

  private long userId;

  private long concertId;

  private String token;

  public RedisTokenRequest(long concertId, String token) {
    this.concertId = concertId;
    this.token = token;
  }
}
