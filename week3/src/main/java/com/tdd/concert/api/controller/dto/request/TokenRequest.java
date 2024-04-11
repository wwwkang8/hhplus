package com.tdd.concert.api.controller.dto.request;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class TokenRequest {

  private long userId;

  private String token;


  public TokenRequest(long userId) {
    this.userId = userId;
  }

  public TokenRequest(String token) {
    this.token = token;
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
}
