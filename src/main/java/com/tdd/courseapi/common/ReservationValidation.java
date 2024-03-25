package com.tdd.courseapi.common;

import java.time.LocalDateTime;

import com.tdd.courseapi.entity.ReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationValidation {

  private final ReservationManager reservationManager;

  public boolean validateRequest(long userId) {

    // 날짜 검증 : 2024년 4월 20일 00:00부터 시작
    if(LocalDateTime.now().isBefore(LocalDateTime.of(2024, 4, 20, 13, 00))){
      throw new RuntimeException("강의 등록기간이 아닙니다.");
    }

    // 인원수 검증 : 30명이 꽉 찼는지 검증
    int count = reservationManager.getCurrentReservationCount();
    if(count == 30) {
      throw new RuntimeException("30명 정원이 마감되었습니다.");
    }

    // 기등록여부 조회
    ReservationEntity reservation = reservationManager.getReservation(userId);
    if(reservation != null) {
      throw new RuntimeException("이미 강의가 신청되어있습니다.");
    }

    return true;
  }

}
