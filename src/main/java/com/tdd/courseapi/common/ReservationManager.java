package com.tdd.courseapi.common;

import java.util.List;

import com.tdd.courseapi.entity.ReservationEntity;
import com.tdd.courseapi.entity.ReservationStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationManager{

  private final ReservationReader reservationReader;
  private final ReservationWriter reservationWriter;

  public ReservationEntity getReservation(long userId) {
    return reservationReader.readReservation(userId);
  }

  public List<ReservationEntity> getReservationList() {
    return reservationReader.readReservationList();
  }

  public ReservationStatus getSuccessFail(long userId) {
    return reservationReader.getSuccessFail(userId);
  }

  public int getCurrentReservationCount() {
    return reservationReader.readReservationList().size();
  }
}
