package com.tdd.concert.domain.seat_optimistic.infra;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.seat_optimistic.model.SeatO;
import com.tdd.concert.domain.seat_optimistic.model.SeatStatusO;
import com.tdd.concert.domain.seat_optimistic.repository.SeatCoreRepositoryO;
import com.tdd.concert.domain.seat_optimistic.repository.SeatJpaRepositoryO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class SeatCoreRepositoryOImpl implements SeatCoreRepositoryO {

  private final SeatJpaRepositoryO seatJpaRepositoryO;

  @Override
  public SeatO findSeatOBySeatId(Long seatId) {
    return seatJpaRepositoryO.findSeatOBySeatId(seatId);
  }

  @Override
  public SeatO findSeatBySeatNoWithOptimisticLock(Long seatId) {
    return seatJpaRepositoryO.findSeatBySeatNoWithOptimisticLock(seatId);
  }

  @Override
  public List<SeatO> findTempReservationExpiredSeatList(SeatStatusO seatStatus) {
    return seatJpaRepositoryO.findTempReservationExpiredSeatList(seatStatus);
  }

  @Override
  public SeatO save(SeatO seat) {
    return seatJpaRepositoryO.save(seat);
  }

}
