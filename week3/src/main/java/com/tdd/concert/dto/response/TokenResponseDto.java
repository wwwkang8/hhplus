package com.tdd.concert.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
public class TokenResponseDto {

  private long userId;

  private String token;

  private long waitNo;

  public TokenResponseDto(long userId, String token, long waitNo) {
    this.userId = userId;
    this.token = token;
    this.waitNo = waitNo;
  }

  public TokenResponseDto() {
  }

  public long getUserId() {
    return userId;
  }

  public String getToken() {
    return token;
  }

  public long getWaitNo() {
    return waitNo;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setWaitNo(long waitNo) {
    this.waitNo = waitNo;
  }
}
