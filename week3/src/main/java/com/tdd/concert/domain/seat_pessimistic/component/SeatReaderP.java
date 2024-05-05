package com.tdd.concert.domain.seat_pessimistic.component;

import java.time.LocalDate;

import com.tdd.concert.domain.seat_pessimistic.model.SeatP;
import com.tdd.concert.domain.seat_pessimistic.repository.SeatCoreRepositoryP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatReaderP {

  private final SeatCoreRepositoryP seatCoreRepositoryP;

  public SeatP findSeatPBySeatId(Long seatId) {
    return seatCoreRepositoryP.findSeatPBySeatId(seatId);
  }


  /** 좌석번호를 조회하는 메서드. */
  public SeatP findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate) {
    return seatCoreRepositoryP.findSeatBySeatNoAndConcert(seatNo, concertId, concertDate);
  }

  /** 비관적락으로 좌석을 조회하는 메서드
   * 좌석예약시에만 활용한다. */
  public SeatP findSeatBySeatNoWithExclusiveLock(Long seatId) {
    return seatCoreRepositoryP.findSeatBySeatNoWithExclusiveLock(seatId);
  }

}
