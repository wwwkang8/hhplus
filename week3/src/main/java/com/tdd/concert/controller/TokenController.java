package com.tdd.concert.controller;

import com.tdd.concert.dto.request.TokenRequestDto;
import com.tdd.concert.dto.response.TokenResponseDto;
import com.tdd.concert.usecase.CreateTokenUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authorization")
@RequiredArgsConstructor
public class TokenController {

  private final CreateTokenUseCase createTokenUseCase;


  @PostMapping("/token")
  public TokenResponseDto createToken() {
    // 임시코드
    TokenRequestDto request = new TokenRequestDto();
    return createTokenUseCase.generateToken(request);
  }
}
