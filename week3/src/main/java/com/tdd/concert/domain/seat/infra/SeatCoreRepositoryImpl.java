package com.tdd.concert.domain.seat.infra;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.repository.SeatCoreRepository;
import com.tdd.concert.domain.seat.repository.SeatJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SeatCoreRepositoryImpl implements SeatCoreRepository {

  private final SeatJpaRepository seatJpaRepository;

  @Override
  public Seat findSeatBySeatNoAndConcert(Long seatNo, Concert concert) {
    return seatJpaRepository.findSeatBySeatNoAndConcert(seatNo, concert);
  }

  @Override
  public Seat findSeatBySeatNoWithExclusiveLock(Long seatNo, Long concertId) {
    return seatJpaRepository.findSeatBySeatNoWithExclusiveLock(seatNo, concertId);
  }

  @Override
  public Seat save(Seat seat) {
    return seatJpaRepository.save(seat);
  }
}
