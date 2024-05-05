package com.tdd.concert.domain.seat_distribute.component;

import java.time.LocalDate;

import com.tdd.concert.domain.seat_distribute.model.SeatD;
import com.tdd.concert.domain.seat_distribute.repository.SeatCoreRepositoryD;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatReaderD {

  private final SeatCoreRepositoryD seatCoreRepositoryD;


  /** 좌석번호를 조회하는 메서드. */
  public SeatD findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate) {
    return seatCoreRepositoryD.findSeatBySeatNoAndConcert(seatNo, concertId, concertDate);
  }

  /** 비관적락으로 좌석을 조회하는 메서드
   * 좌석예약시에만 활용한다. */
  public SeatD findSeatDBySeatId(Long seatId) {
    return seatCoreRepositoryD.findSeatDBySeatId(seatId);
  }

}
