package com.tdd.concert.api.usecase;

import com.tdd.concert.domain.token.component.TokenManager;
import com.tdd.concert.api.controller.dto.request.TokenRequest;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CreateTokenUseCase {

  private final TokenManager tokenManager;


  public TokenResponse insertQueue(HttpServletRequest request) {

    String token = request.getHeader("Authorization");
    TokenRequest tokenRequest = new TokenRequest(token);

    if(tokenRequest.getToken() == null) {
      return tokenManager.insertQueue(tokenRequest);
    }else {
      return tokenManager.validateToken(tokenRequest);
    }
  }


}
