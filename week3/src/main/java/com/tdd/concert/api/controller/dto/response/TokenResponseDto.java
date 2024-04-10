package com.tdd.concert.api.controller.dto.response;

import java.time.LocalDateTime;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.status.ProgressStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
public class TokenResponseDto {

  private long userId;

  private String token;

  private long waitNo;

  private ProgressStatus status;

  private LocalDateTime expiredAt;

  public TokenResponseDto() {
  }

  public TokenResponseDto(long userId, String token, long waitNo) {
    this.userId = userId;
    this.token = token;
    this.waitNo = waitNo;
  }

  public TokenResponseDto(long userId, String token, long waitNo,
                          ProgressStatus status) {
    this.userId = userId;
    this.token = token;
    this.waitNo = waitNo;
    this.status = status;
  }

  public TokenResponseDto(long userId, String token, long waitNo, ProgressStatus status,
                          LocalDateTime expiredAt) {
    this.userId = userId;
    this.token = token;
    this.waitNo = waitNo;
    this.status = status;
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

  public ProgressStatus getStatus() {
    return status;
  }

  public void setStatus(ProgressStatus status) {
    this.status = status;
  }

  public static TokenResponseDto from(Token token) {
    return new TokenResponseDto(
        token.getUser().getUserId(),
        token.getToken(),
        token.getWaitNo(),
        token.getProgressStatus(),
        token.getExpiredAt()
    );
  }

}
