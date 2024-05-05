package com.tdd.concert.domain.seat_distribute.infra;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.seat_distribute.model.SeatD;
import com.tdd.concert.domain.seat_distribute.model.SeatStatusD;
import com.tdd.concert.domain.seat_distribute.repository.SeatCoreRepositoryD;
import com.tdd.concert.domain.seat_distribute.repository.SeatJpaRepositoryD;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SeatCoreRepositoryDImpl implements SeatCoreRepositoryD {

  private final SeatJpaRepositoryD seatJpaRepositoryD;


  @Override
  public SeatD findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate) {
    return seatJpaRepositoryD.findSeatBySeatNoAndConcert(seatNo, concertId, concertDate);
  }

  @Override
  public SeatD findSeatDBySeatId(Long seatId) {
    return seatJpaRepositoryD.findSeatDBySeatId(seatId);
  }

  @Override
  public List<SeatD> findTempReservationExpiredSeatList(SeatStatusD seatStatus) {
    return seatJpaRepositoryD.findTempReservationExpiredSeatList(seatStatus);
  }

}
