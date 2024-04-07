package com.tdd.concert.domain.token.component;

import com.tdd.concert.dto.request.TokenRequestDto;
import com.tdd.concert.dto.response.TokenResponseDto;

public interface TokenManager {

  public TokenResponseDto insertQueue(TokenRequestDto tokenRequestDto);

//  public TokenResponseDto validateToken(TokenRequestDto tokenRequestDto);
//
//  public TokenResponseDto selectToken(TokenRequestDto tokenRequestDto);

}
