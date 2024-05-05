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

  private final SeatJpaRepositoryP seatJpaRepositoryP;

  @Override
  public SeatP findSeatPBySeatId(Long seatId) {
    return seatJpaRepositoryP.findSeatPBySeatId(seatId);
  }

  @Override
  public SeatP findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate) {
    return seatJpaRepositoryP.findSeatBySeatNoAndConcert(seatNo, concertId, concertDate);
  }

  @Override
  public SeatP findSeatBySeatNoWithExclusiveLock(Long seatId) {
    return seatJpaRepositoryP.findSeatBySeatNoWithExclusiveLock(seatId);
  }

  @Override
  public List<SeatP> findTempReservationExpiredSeatList(SeatStatusP seatStatus) {
    return seatJpaRepositoryP.findTempReservationExpiredSeatList(seatStatus);
  }

  @Override
  public SeatP save(SeatP seat) {
    return seatJpaRepositoryP.save(seat);
  }

}
