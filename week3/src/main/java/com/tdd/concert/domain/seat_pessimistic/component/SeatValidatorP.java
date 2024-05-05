package com.tdd.concert.domain.seat_pessimistic.component;

import com.tdd.concert.domain.seat_pessimistic.model.SeatP;
import com.tdd.concert.domain.seat_pessimistic.model.SeatStatusP;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatValidatorP {

  public void validate(SeatP seatP) {

    if(seatP.getSeatStatusP() == SeatStatusP.TEMPORARY_RESERVED) {
      throw new RuntimeException("이미 다른 사용자에게 임시배정된 좌석입니다. 좌석번호 : " + seatP.getSeatNo());

    } else if(seatP.getSeatStatusP() == SeatStatusP.SOLDOUT) {
      throw new RuntimeException("이미 판매완료된 좌석입니다. 좌석번호 : " + seatP.getSeatNo());
    }

  }


}
