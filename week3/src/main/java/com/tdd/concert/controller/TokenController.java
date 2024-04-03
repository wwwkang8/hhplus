package com.tdd.concert.controller;

import com.tdd.concert.dto.request.TokenRequestDto;
import com.tdd.concert.dto.response.TokenResponseDto;
import com.tdd.concert.usecase.CreateTokenUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/queue")
public class TokenController {

  private final CreateTokenUseCase createTokenUseCase;

  public TokenController(CreateTokenUseCase createTokenUseCase) {
    this.createTokenUseCase = createTokenUseCase;
  }

  @PostMapping("/insertQueue")
  public String insertQueue() {
    // Mock 코드
    TokenRequestDto request = new TokenRequestDto(1L);

    return createTokenUseCase.insertQueue(request).toString();
  }
}
