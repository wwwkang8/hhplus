package com.tdd.concert.domain.seat_optimistic.component;

import com.tdd.concert.domain.seat_optimistic.model.SeatO;
import com.tdd.concert.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatManagerO {

  private final SeatReaderO seatReaderO;
  private final SeatValidatorO seatValidatorO;

  public SeatO occupy(Long seatPId, User user) {
     // 낙관적 락으로 좌석 조회
    SeatO occupySeatO = seatReaderO.findSeatBySeatNoWithOptimisticLock(seatPId);

    /** 좌석 검증처리 */
    seatValidatorO.validate(occupySeatO);

    occupySeatO.tempOccupy(user.getUserId());

    return occupySeatO;
  }
}
