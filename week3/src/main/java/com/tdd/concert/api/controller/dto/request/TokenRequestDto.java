package com.tdd.concert.api.controller.dto.request;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public class TokenRequestDto {

  private long userId;

  private String token;


  public TokenRequestDto(long userId) {
    this.userId = userId;
  }

  public TokenRequestDto(String token) {
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