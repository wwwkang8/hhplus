package com.tdd.courseapi.service;

import java.util.List;

import com.tdd.courseapi.entity.ReservationEntity;
import com.tdd.courseapi.entity.ReservationStatus;

public interface ReservationService {

  public ReservationEntity reserve(long userId);

  public ReservationEntity getReservation(long userId);

  public int getCurrentReservationCount();

  public ReservationStatus getSuccessFail(long userId);

}
