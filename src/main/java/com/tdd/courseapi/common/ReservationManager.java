package com.tdd.courseapi.common;

import com.tdd.courseapi.entity.ReservationEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationManager implements ReservationManagerJob{

  private final ReservationReader reservationReader;
  private final ReservationWriter reservationWriter;

  public ReservationEntity read(long userId) {
    return reservationReader.readReservation(userId);
  }



}
