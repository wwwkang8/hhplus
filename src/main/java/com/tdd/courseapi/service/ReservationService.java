package com.tdd.courseapi.service;

import com.tdd.courseapi.domain.ReservationEntity;
import com.tdd.courseapi.constant.ReservationStatus;

public interface ReservationService {

  public ReservationStatus reserve(long userId);

  public int getCurrentReservationCount();

  public ReservationStatus getSuccessFail(long userId);

}
