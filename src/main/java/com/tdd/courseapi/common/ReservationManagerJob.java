package com.tdd.courseapi.common;

import com.tdd.courseapi.entity.ReservationEntity;

public interface ReservationManagerJob {

  public ReservationEntity read(long userId);

}
