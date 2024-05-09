package com.tdd.concert.api.controller.dto.request;

public class RedisTokenRequest {

  private long userId;

  private long concertId;

  private String token;

  public RedisTokenRequest(long concertId, String token) {
    this.concertId = concertId;
    this.token = token;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getConcertId() {
    return concertId;
  }

  public void setConcertId(long concertId) {
    this.concertId = concertId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
