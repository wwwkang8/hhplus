package com.tdd.concert.domain.token.component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.repository.TokenCoreRepository;
import com.tdd.concert.domain.token.repository.TokenRepository;
import com.tdd.concert.domain.token.status.ProgressStatus;
import com.tdd.concert.dto.request.TokenRequestDto;
import com.tdd.concert.dto.response.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenGenerator {

  // 생성자 주입
  private final TokenCoreRepository tokenCoreRepository;
  private final TokenRepository tokenRepository;

  public Token insertTokenTable(Token token) {
    return tokenCoreRepository.save(token);
  }

  public String generateToken(long userId) {

    /** 사용자ID로 토큰 생성 */
    String combinedData = userId + "";
    String token = UUID.nameUUIDFromBytes(combinedData.getBytes()).toString();

    log.info("[토큰 생성] UUID 토큰 : " + token + ", 사용자ID : " + userId);

    return token;
  }

  /** 대기순번 채번 */
  public long selectLastWaitNo() {
    return tokenCoreRepository.selectNextWaitNo();
  }

  /** 현재 대기열의 대기상태 확인 */
  public ProgressStatus getCurrentQueueStatus() {
    // 1. 현재 예약 진행중인 고객들이 몇 명인지 확인
    long count = tokenRepository.getCurrentReservationOngoingCount(ProgressStatus.ONGOING);

    log.info("[getCurrentQueueStatus] 예약 진행중인 고객수 : " + count);

    // 2. 예약중 고객이 50명 미만이면 바로 예약가능상태를 리턴, 50명이상이면 대기상태로 리턴
    if( count < 50 ) {
      return ProgressStatus.ONGOING;
    }else{
      return ProgressStatus.WAIT;
    }
  }
}
