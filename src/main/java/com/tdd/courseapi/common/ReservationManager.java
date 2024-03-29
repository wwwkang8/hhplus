package com.tdd.courseapi.common;

import java.util.List;

import com.tdd.courseapi.domain.ReservationEntity;
import com.tdd.courseapi.constant.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationManager{

  private final ReservationReader reservationReader;
  private final ReservationWriter reservationWriter;

  public ReservationStatus getSuccessFail(long userId, long courseId) {
    return reservationReader.getSuccessFail(userId, courseId);
  }

  public int countByCourseEntityCourseId(long courseId) {
    return reservationReader.countByCourseEntityCourseId(courseId);
  }

  public ReservationStatus reserve(long userId, long courseId) {
      reservationWriter.reserve(userId, courseId);
      return reservationReader.getSuccessFail(userId, courseId) ;
  }

  public ReservationEntity getReservation(long userId, long courseId) {
    return reservationReader.readReservation(userId, courseId);
  }
}
