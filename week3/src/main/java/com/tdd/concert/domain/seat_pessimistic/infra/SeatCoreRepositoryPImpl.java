package com.tdd.concert.domain.seat_pessimistic.infra;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.seat_pessimistic.model.SeatP;
import com.tdd.concert.domain.seat_pessimistic.model.SeatStatusP;
import com.tdd.concert.domain.seat_pessimistic.repository.SeatCoreRepositoryP;
import com.tdd.concert.domain.seat_pessimistic.repository.SeatJpaRepositoryP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SeatCoreRepositoryPImpl implements SeatCoreRepositoryP {

  private final SeatJpaRepositoryP seatJpaRepository;

  @Override
  public SeatP findSeatP(Long seatId) {
    return seatJpaRepository.findSeatPBySeatId(seatId);
  }

  @Override
  public SeatP findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate) {
    return seatJpaRepository.findSeatBySeatNoAndConcert(seatNo, concertId, concertDate);
  }

  @Override
  public SeatP findSeatBySeatNoWithExclusiveLock(Long seatId) {
    return seatJpaRepository.findSeatBySeatNoWithExclusiveLock(seatId);
  }

  @Override
  public List<SeatP> findTempReservationExpiredSeatList(SeatStatusP seatStatus) {
    return seatJpaRepository.findTempReservationExpiredSeatList(seatStatus);
  }

  @Override
  public SeatP save(SeatP seat) {
    return seatJpaRepository.save(seat);
  }

}
