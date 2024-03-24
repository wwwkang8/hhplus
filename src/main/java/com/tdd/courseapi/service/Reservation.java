package com.tdd.courseapi.service;

import java.util.List;

public interface Reservation {

  public Reservation reserve(long userId);

  public Reservation getReservation(long id, long userId);

  public List<Reservation> getReservationList(long userId);

}
