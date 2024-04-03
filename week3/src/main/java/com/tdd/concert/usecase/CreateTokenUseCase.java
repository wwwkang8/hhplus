package com.tdd.concert.usecase;

import com.tdd.concert.domain.token.component.TokenManagerImpl;
import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.dto.request.TokenRequestDto;
import com.tdd.concert.dto.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class CreateTokenUseCase {

  private final TokenManagerImpl tokenManagerImpl;

  public CreateTokenUseCase(TokenManagerImpl tokenManagerImpl) {
    this.tokenManagerImpl = tokenManagerImpl;
  }

  // 토큰 발행
  public TokenResponseDto generateToken(TokenRequestDto request) {
    return tokenManagerImpl.generateToken(request);
  }

  public TokenResponseDto insertQueue(TokenRequestDto request) {

    return tokenManagerImpl.insertQueue(request);
  }


}
