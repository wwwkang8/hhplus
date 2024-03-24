package com.tdd.courseapi.service;

import com.tdd.courseapi.entity.ReservationEntity;
import com.tdd.courseapi.constant.ReservationStatus;

public interface ReservationService {

  public ReservationStatus reserve(long userId);

  public ReservationEntity getReservation(long userId);

  public int getCurrentReservationCount();

  public ReservationStatus getSuccessFail(long userId);

}
