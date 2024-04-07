package com.tdd.concert.controller;

import com.tdd.concert.dto.request.TokenRequestDto;
import com.tdd.concert.dto.response.TokenResponseDto;
import com.tdd.concert.usecase.CreateTokenUseCase;
import com.tdd.concert.usecase.ValidateTokenUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
@Slf4j
public class TokenController {

  private final CreateTokenUseCase createTokenUseCase;
  private final ValidateTokenUseCase validateTokenUseCase;


  @PostMapping("")
  public ResponseEntity<TokenResponseDto> insertQueue(HttpServletRequest request) {
    return ResponseEntity.ok().body(createTokenUseCase.insertQueue(request));
  }

  @GetMapping("")
  public ResponseEntity<TokenResponseDto> validateToken(HttpServletRequest request) {
    return ResponseEntity.ok().body(validateTokenUseCase.validateToken(request));
  }
}
