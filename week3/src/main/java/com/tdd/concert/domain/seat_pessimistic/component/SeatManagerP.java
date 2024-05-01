package com.tdd.concert.domain.seat_pessimistic.component;

import java.time.LocalDate;

import com.tdd.concert.domain.seat_pessimistic.model.SeatP;
import com.tdd.concert.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatManagerP {

  private final SeatReaderP seatReader;

  public SeatP findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate) {
    return seatReader.findSeatBySeatNoAndConcert(seatNo, concertId, concertDate);
  }

  public SeatP occupy(SeatP seatP, User user) {

     // 일단은 일반 조회로직으로 설정
      SeatP occupySeatP = seatReader.findSeatPBySeatId(seatP.getSeatId());

    occupySeatP.tempOccupy(user.getUserId());

    return seatP;
  }
}
