package com.tdd.courseapi.service;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ReservationServiceImpl implements Reservation{
  @Override
  public Reservation reserve(long userId) {
    return null;
  }

  @Override
  public Reservation getReservation(long id, long userId) {
    return null;
  }

  @Override
  public List<Reservation> getReservationList(long userId) {
    return null;
  }
}
