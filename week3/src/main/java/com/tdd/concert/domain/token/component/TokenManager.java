package com.tdd.concert.domain.token.component;

import com.tdd.concert.api.controller.dto.request.TokenRequest;
import com.tdd.concert.api.controller.dto.response.TokenResponse;

public interface TokenManager {

  public TokenResponse insertQueue(TokenRequest tokenRequest);
  public TokenResponse validateToken(TokenRequest tokenRequest);

//  public TokenResponseDto validateToken(TokenRequestDto tokenRequestDto);
//
//  public TokenResponseDto selectToken(TokenRequestDto tokenRequestDto);

}
