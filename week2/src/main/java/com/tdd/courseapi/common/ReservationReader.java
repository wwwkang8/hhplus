package com.tdd.courseapi.common;

import java.util.List;

import com.tdd.courseapi.domain.ReservationEntity;
import com.tdd.courseapi.constant.ReservationStatus;
import com.tdd.courseapi.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationReader {

  private final ReservationRepository reservationRepository;

  // 사용자 아이디로 예약내역 조회
  public ReservationEntity readReservation(long userId, long courseId) {
    return reservationRepository.findByUserIdAndCourseId(userId, courseId);
  }

  public int countByCourseEntityCourseId(long courseId) {
    return reservationRepository.countByCourseEntityCourseId(courseId);
  }

  // 사용자 아이디로 예약상태(성공 or 실패) 조회
  public ReservationStatus getSuccessFail(long userId, long courseId) {

    if (readReservation(userId, courseId) == null) {
      return ReservationStatus.FAIL;
    } else {
      return ReservationStatus.SUCCESS;
    }
  }


}
