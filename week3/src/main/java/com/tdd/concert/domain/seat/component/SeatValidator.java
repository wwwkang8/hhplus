package com.tdd.concert.domain.seat.component;

import java.time.LocalDate;

import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import com.tdd.concert.domain.seat.repository.SeatCoreRepository;
import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatValidator {

  private final SeatCoreRepository seatCoreRepository;

  public void validate(Seat seat) {

    if(seat.getSeatStatus() == SeatStatus.TEMPORARY_RESERVED) {
      throw new RuntimeException("이미 다른 사용자에게 임시배정된 좌석입니다. 좌석번호 : " + seat.getSeatNo());

    } else if(seat.getSeatStatus() == SeatStatus.SOLDOUT) {
      throw new RuntimeException("이미 판매완료된 좌석입니다. 좌석번호 : " + seat.getSeatNo());
    }

  }


}
