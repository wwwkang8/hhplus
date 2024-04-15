package com.tdd.concert.domain.token.component;

import com.tdd.concert.api.controller.dto.request.TokenRequest;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
import com.tdd.concert.domain.token.model.Token;

public interface TokenManager {

  public TokenResponse insertQueue(TokenRequest tokenRequest);
  public TokenResponse validateToken(TokenRequest tokenRequest);
  public void expireToken(String token);

}
