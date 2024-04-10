package com.tdd.concert.domain.token.component;

import java.time.LocalDateTime;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.repository.TokenCoreRepository;
import com.tdd.concert.domain.token.status.ProgressStatus;
import com.tdd.concert.api.controller.dto.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenValidator {

  private final TokenCoreRepository tokenCoreRepository;

  public TokenResponseDto validateToken(String token) {

    Token selectedToken = tokenCoreRepository.findByToken(token);

    // 대기열에 존재하지 않는 토큰인 경우
    if(selectedToken == null) {
      throw new RuntimeException("대기열에 존재하지 않는 토큰입니다. 토큰 : " + token);
    }

    // 만료시각이 지난 토큰인 경우
    if(selectedToken.getExpiredAt().isBefore(LocalDateTime.now())) {
      throw new RuntimeException("만료시각이 지난 토큰입니다. 토큰 : " + selectedToken.getToken() + ", 토큰만료시각 : " + selectedToken.getExpiredAt());
    }

    // 토큰 상태가 "예약완료" 상태인 경우
    if(selectedToken.getProgressStatus() == ProgressStatus.FINISHED) {
      throw new RuntimeException("예약이 완료된 토큰입니다. 토큰 : " + selectedToken.getToken() + ", 토큰 상태 : " + selectedToken.getProgressStatus());
    }

    return TokenResponseDto.from(selectedToken);

  }

}
