package com.tdd.concert.api.controller;

import com.tdd.concert.api.controller.dto.response.TokenResponse;
import com.tdd.concert.api.usecase.CreateTokenUseCase;
import com.tdd.concert.api.usecase.ValidateTokenUseCase;
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
  public ResponseEntity<TokenResponse> insertQueue(HttpServletRequest request) {
    return ResponseEntity.ok().body(createTokenUseCase.insertQueue(request));
  }

  @GetMapping("")
  public ResponseEntity<TokenResponse> validateToken(HttpServletRequest request) {
    return ResponseEntity.ok().body(validateTokenUseCase.validateToken(request));
  }
}
