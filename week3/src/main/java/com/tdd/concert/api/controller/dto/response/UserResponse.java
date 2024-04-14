package com.tdd.concert.api.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class UserResponse {

  private long userId;

  private String token;

  private int point;

}
