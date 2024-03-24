package com.tdd.courseapi.common;

import com.tdd.courseapi.entity.ReservationEntity;
import com.tdd.courseapi.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationReader {

  private final ReservationRepository reservationRepository;

  // 사용자 아이디로 예약내역 조회
  public ReservationEntity readReservation(long userId) {
    return reservationRepository.findByUserId(userId);
  }


}
