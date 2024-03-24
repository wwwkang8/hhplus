package com.tdd.courseapi.common;

import java.util.List;

import com.tdd.courseapi.entity.ReservationEntity;
import com.tdd.courseapi.entity.ReservationStatus;
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

  public List<ReservationEntity> readReservationList() {
    return reservationRepository.findAll();
  }

  // 사용자 아이디로 예약상태(성공 or 실패) 조회
  public ReservationStatus getSuccessFail(long userId) {
    return readReservation(userId).getReservationStatus();
  }



}
