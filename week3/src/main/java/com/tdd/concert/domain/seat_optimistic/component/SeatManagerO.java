package com.tdd.concert.domain.seat_optimistic.component;

import java.time.LocalDate;

import com.tdd.concert.domain.seat_optimistic.model.SeatO;
import com.tdd.concert.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatManagerO {

  private final SeatReaderO seatReader;

  public SeatO occupy(Long seatNo, Long concertId, LocalDate concertDate, User user) {
    log.info("[SeatManagerO] occupy 진입");

     // 일단은 일반 조회로직으로 설정
    SeatO occupySeatO = seatReader.findSeatBySeatNoWithOptimisticLock(seatNo, concertId, concertDate);

    occupySeatO.tempOccupy(user.getUserId());

    return occupySeatO;
  }
}
