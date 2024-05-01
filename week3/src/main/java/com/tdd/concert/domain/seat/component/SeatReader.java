package com.tdd.concert.domain.seat.component;

import java.time.LocalDate;

import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.repository.SeatCoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatReader {

  private final SeatCoreRepository seatCoreRepository;


  /** 좌석번호를 조회하는 메서드. */
  public Seat findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate) {
    return seatCoreRepository.findSeatBySeatNoAndConcert(seatNo, concertId, concertDate);
  }

  /** 비관적락으로 좌석을 조회하는 메서드
   * 좌석예약시에만 활용한다. */
  public Seat findSeatBySeatNoWithExclusiveLock(Long seatNo, Long concertId, LocalDate concertDate) {
    return seatCoreRepository.findSeatBySeatNoWithExclusiveLock(seatNo, concertId, concertDate);
  }

}
