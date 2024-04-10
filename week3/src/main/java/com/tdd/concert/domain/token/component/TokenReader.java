package com.tdd.concert.domain.token.component;

import com.tdd.concert.domain.token.repository.TokenCoreRepository;
import com.tdd.concert.domain.token.status.ProgressStatus;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenReader {

  // 생성자 주입
  private final TokenCoreRepository tokenCoreRepository;

  /** 대기순번 채번 */
  public long selectNextWaitNo() {
    return tokenCoreRepository.selectNextWaitNo();
  }

  /** 현재 예약 진행중인 고객이 몇명인지 조회 */
  public long getProgressStatusCount(ProgressStatus status) {
    return tokenCoreRepository.getProgressStatusCount(status);
  }

  /** 현재 대기열의 대기상태 확인 */
  public ProgressStatus getCurrentQueueStatus() {
    // 1. 현재 예약 진행중인 고객들이 몇 명인지 확인
    long count = tokenCoreRepository.getProgressStatusCount(ProgressStatus.ONGOING);

    log.info("[getCurrentQueueStatus] 예약 진행중인 고객수 : " + count);

    // 2. 예약중 고객이 50명 미만이면 바로 예약가능상태를 리턴, 50명이상이면 대기상태로 리턴
    if( count < 50 ) {
      return ProgressStatus.ONGOING;
    }else{
      return ProgressStatus.WAIT;
    }
  }

}
