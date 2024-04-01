package com.tdd.concert.domain.token.component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.tdd.concert.dto.request.TokenRequestDto;
import com.tdd.concert.dto.response.TokenResponseDto;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {

  public TokenResponseDto generateToken(TokenRequestDto request) {

    // 토큰 생성시각과 사용자 아이디를 조합하여 UUID 생성
//    String createdTime = LocalDateTime.now().toString();
//    long userId = request.getUserId();
//
//    String combinedData = userId + "-" + createdTime;
//
//    String token = UUID.nameUUIDFromBytes(combinedData.getBytes()).toString();
//
//    TokenResponseDto response = new TokenResponseDto(userId, token);

    return null;
  }

}
