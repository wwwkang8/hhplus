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
@RequiredArgsConstructor
@NoArgsConstructor
public class TokenResponseDto {

  private long userId;

  private String token;

  private long order;

  public TokenResponseDto(long userId, String token) {
    this.userId = userId;
    this.token = token;
  }
}
