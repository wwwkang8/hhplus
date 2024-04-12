package com.tdd.concert.domain.concert.infra;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.concert.repository.ConcertCoreRepository;
import com.tdd.concert.domain.concert.repository.ConcertJpaRepository;
import com.tdd.concert.domain.concert.status.ReservationStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ConcertCoreRepositoryImpl implements ConcertCoreRepository {

  private final ConcertJpaRepository concertJpaRepository;

  @Override
  public List<LocalDate> availableConcertDate(long concertId) {
    return concertJpaRepository.availableConcertDate(concertId);
  }

  @Override
  public List<Long> seatNoList(long concertId, LocalDate concertDate, ReservationStatus reservationStatus) {
    return concertJpaRepository.seatNoList(concertId, concertDate, reservationStatus);
  }

  @Override
  public Concert findConcertByConcertId(Long concertId) {
    return concertJpaRepository.findConcertByConcertId(concertId);
  }
}
