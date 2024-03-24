package com.tdd.courseapi.stub;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tdd.courseapi.entity.ReservationEntity;
import com.tdd.courseapi.constant.ReservationStatus;

public class ReservationManagerStub implements ReservationManagerJob {

  public ReservationEntity read(long userId) {
    return new ReservationEntity(1L, 10L, "001", LocalDateTime.now(), LocalDate.now(), ReservationStatus.SUCCESS, 1L);
  }

  }

