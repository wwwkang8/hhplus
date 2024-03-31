package com.tdd.concert.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
public class TokenRequestDto {

  private long userId;

  private String token;


  public TokenRequestDto(long userId) {
    this.userId = userId;
  }
}
