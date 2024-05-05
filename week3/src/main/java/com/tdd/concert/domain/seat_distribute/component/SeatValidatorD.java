package com.tdd.concert.domain.seat_distribute.component;

import com.tdd.concert.domain.seat_distribute.model.SeatD;
import com.tdd.concert.domain.seat_distribute.model.SeatStatusD;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatValidatorD {

  public void validate(SeatD seatD) {

    if(seatD.getSeatStatusD() == SeatStatusD.TEMPORARY_RESERVED) {
      throw new RuntimeException("이미 다른 사용자에게 임시배정된 좌석입니다. 좌석번호 : " + seatD.getSeatNo());

    } else if(seatD.getSeatStatusD() == SeatStatusD.SOLDOUT) {
      throw new RuntimeException("이미 판매완료된 좌석입니다. 좌석번호 : " + seatD.getSeatNo());
    }

  }


}
