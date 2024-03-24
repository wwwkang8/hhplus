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
      return false;
    }

    // 인원수 검증 : 30명이 꽉 찼는지 검증
    int count = reservationManager.getCurrentReservationCount();
    if(count == 30) {
      return false;
    }

    // 기등록여부 조회
    ReservationEntity reservation = reservationManager.getReservation(userId);
    if(reservation != null) {
      return false;
    }

    return true;
  }

}
