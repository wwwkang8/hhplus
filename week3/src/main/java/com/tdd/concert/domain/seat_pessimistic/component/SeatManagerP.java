package com.tdd.concert.domain.seat_pessimistic.component;

import com.tdd.concert.domain.seat_pessimistic.model.SeatP;
import com.tdd.concert.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatManagerP {

  private final SeatValidatorP seatValidatorP;
  private final SeatReaderP seatReaderP;

  public SeatP findSeatPBySeatId(Long seatId) {
    return seatReaderP.findSeatPBySeatId(seatId);
  }

  public SeatP occupy(Long seatPId, User user) {

     // 비관적 락으로 좌석 조회(읽기, 쓰기 잠금)
     SeatP occupySeatP = seatReaderP.findSeatBySeatNoWithExclusiveLock(seatPId);

    seatValidatorP.validate(occupySeatP);

    occupySeatP.tempOccupy(user.getUserId());

    return occupySeatP;
  }
}
