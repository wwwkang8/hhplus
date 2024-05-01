package com.tdd.concert.domain.seat_optimistic.component;

import java.time.LocalDate;

import com.tdd.concert.domain.seat_optimistic.model.SeatO;
import com.tdd.concert.domain.seat_optimistic.repository.SeatCoreRepositoryO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatReaderO {

  private final SeatCoreRepositoryO seatCoreRepositoryO;

  public SeatO findSeatOBySeatId(Long seatId) {
    return seatCoreRepositoryO.findSeatOBySeatId(seatId);
  }


  /** 비관적락으로 좌석을 조회하는 메서드
   * 좌석예약시에만 활용한다. */
  public SeatO findSeatBySeatNoWithOptimisticLock(Long seatNo, Long concertId, LocalDate concertDate) {
    return seatCoreRepositoryO.findSeatBySeatNoWithOptimisticLock(seatNo, concertId, concertDate);
  }

}
