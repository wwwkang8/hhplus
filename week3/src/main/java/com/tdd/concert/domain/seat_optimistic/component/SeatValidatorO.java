package com.tdd.concert.domain.seat_optimistic.component;

import com.tdd.concert.domain.seat_optimistic.model.SeatO;
import com.tdd.concert.domain.seat_optimistic.model.SeatStatusO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatValidatorO {

  public void validate(SeatO seatO) {

    if(seatO.getSeatStatusO() == SeatStatusO.TEMPORARY_RESERVED) {
      throw new RuntimeException("이미 다른 사용자에게 임시배정된 좌석입니다. 좌석번호 : " + seatO.getSeatNo());

    } else if(seatO.getSeatStatusO() == SeatStatusO.SOLDOUT) {
      throw new RuntimeException("이미 판매완료된 좌석입니다. 좌석번호 : " + seatO.getSeatNo());
    }

  }


}
