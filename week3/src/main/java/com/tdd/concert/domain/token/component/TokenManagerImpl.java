package com.tdd.concert.domain.token.component;

import com.tdd.concert.dto.request.TokenRequestDto;
import com.tdd.concert.dto.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class TokenManagerImpl implements TokenManager{

  private final TokenGenerator tokenGenerator;
  private final TokenValidator tokenValidator;

  public TokenManagerImpl(TokenGenerator tokenGenerator,
                          TokenValidator tokenValidator) {
    this.tokenGenerator = tokenGenerator;
    this.tokenValidator = tokenValidator;
  }

  // 토큰 발행
  @Override
  public TokenResponseDto generateToken(TokenRequestDto tokenRequestDto) {
    return tokenGenerator.generateToken(tokenRequestDto);
  }

  public TokenResponseDto insertQueue(TokenRequestDto request) {

    // Mock 코드
    TokenResponseDto tokenResponseDto = new TokenResponseDto(1L, "abcd-8789-uj87-89ok", 4L);

    return tokenResponseDto;
  }

//  @Override
//  public TokenResponseDto validateToken(TokenRequestDto tokenRequestDto) {
//    return null;
//  }
//
//  @Override
//  public TokenResponseDto selectToken(TokenRequestDto tokenRequestDto) {
//    return null;
//  }
}
