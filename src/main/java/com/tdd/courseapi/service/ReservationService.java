package com.tdd.courseapi.service;

import java.util.List;

import com.tdd.courseapi.entity.ReservationEntity;

public interface ReservationService {

  public ReservationEntity reserve(long userId);

  public ReservationEntity getReservation(long userId);

  public List<ReservationEntity> getReservationList(long userId);

}
