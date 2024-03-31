package com.tdd.concert.domain.token.component;

import com.tdd.concert.dto.request.TokenRequestDto;
import com.tdd.concert.dto.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenManagerImpl implements TokenManager{

  private final TokenGenerator tokenGenerator;
  private final TokenValidator tokenValidator;

  // 토큰 발행
  @Override
  public TokenResponseDto generateToken(TokenRequestDto tokenRequestDto) {
    return tokenGenerator.generateToken(tokenRequestDto);
  }

  @Override
  public TokenResponseDto validateToken(TokenRequestDto tokenRequestDto) {
    return null;
  }

  @Override
  public TokenResponseDto selectToken(TokenRequestDto tokenRequestDto) {
    return null;
  }
}
