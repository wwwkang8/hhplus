package com.tdd.concert.domain.concert.repository;

import java.time.LocalDate;
import java.util.List;

public interface ConcertCoreRepository {

  public List<LocalDate> availableConcertDate(long concertId);

}
