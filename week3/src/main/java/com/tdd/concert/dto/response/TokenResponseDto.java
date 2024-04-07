package com.tdd.concert.dto.response;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.status.ProgressStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@ToString
@Getter
public class TokenResponseDto {

  private long userId;

  private String token;

  private long waitNo;

  private ProgressStatus status;

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
        token.getProgressStatus()
    );
  }

}
