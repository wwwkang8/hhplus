package com.tdd.concert.api.controller;

import com.tdd.concert.api.controller.dto.request.RedisTokenRequest;
import com.tdd.concert.api.controller.dto.request.TokenRequest;
import com.tdd.concert.api.controller.dto.response.RedisTokenResponse;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
import com.tdd.concert.api.usecase.CreateRedisTokenUseCase;
import com.tdd.concert.api.usecase.CreateTokenUseCase;
import com.tdd.concert.api.usecase.ValidateTokenUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/queue")
@RequiredArgsConstructor
@Slf4j
public class TokenController {

  private final CreateTokenUseCase createTokenUseCase;
  private final CreateRedisTokenUseCase createRedisTokenUseCase;
  private final ValidateTokenUseCase validateTokenUseCase;


  @PostMapping("")
  public ResponseEntity<TokenResponse> insertQueue(HttpServletRequest request) {

    String token = request.getHeader("Authorization");
    TokenRequest tokenRequest = new TokenRequest(token);

    return ResponseEntity.ok().body(createTokenUseCase.insertQueue(tokenRequest));
  }

  @PostMapping("/redis")
  public ResponseEntity<RedisTokenResponse> insertRedisQueue(HttpServletRequest request, @RequestBody RedisTokenRequest redisTokenRequest) {

    String token = request.getHeader("Authorization");

    // 헤더에 있는 토큰을 RedisTokenRequest에 실어서 CreateRedisTokenUseCase르 호출한다.
    RedisTokenRequest argRequest = redisTokenRequest;
    argRequest.setToken(token);

    return ResponseEntity.ok().body(createRedisTokenUseCase.insertRedisQueue(argRequest));
  }

  @GetMapping("")
  public ResponseEntity<TokenResponse> validateToken(HttpServletRequest request) {

    String token = request.getHeader("Authorization");
    TokenRequest tokenRequest = new TokenRequest(token);

    return ResponseEntity.ok().body(validateTokenUseCase.validateToken(tokenRequest));
  }
}
