package com.tdd.concert.controller;

import com.tdd.concert.dto.request.TokenRequestDto;
import com.tdd.concert.dto.response.TokenResponseDto;
import com.tdd.concert.usecase.CreateTokenUseCase;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/queue")
public class TokenController {

  private final CreateTokenUseCase createTokenUseCase;

  public TokenController(CreateTokenUseCase createTokenUseCase) {
    this.createTokenUseCase = createTokenUseCase;
  }

  @PostMapping("")
  public ResponseEntity<TokenResponseDto> insertQueue(HttpServletRequest request) {
    return ResponseEntity.ok().body(createTokenUseCase.insertQueue(request));
  }
}
