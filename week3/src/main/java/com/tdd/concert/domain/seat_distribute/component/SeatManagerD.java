package com.tdd.concert.domain.seat_distribute.component;

import java.time.LocalDate;

import com.tdd.concert.domain.seat_distribute.model.SeatD;
import com.tdd.concert.domain.seat_distribute.redisson.DistributedLock;
import com.tdd.concert.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatManagerD {

  private final SeatValidatorD seatValidatorD;
  private final SeatReaderD seatReaderD;

  public SeatD findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate) {
    return seatReaderD.findSeatBySeatNoAndConcert(seatNo, concertId, concertDate);
  }

  @DistributedLock(key = "seatRedissonLock")
  public SeatD occupy(Long seatDId, User user) {

    SeatD occupySeatD = seatReaderD.findSeatDBySeatId(seatDId);

    seatValidatorD.validate(occupySeatD);

    occupySeatD.tempOccupy(user.getUserId());

    return occupySeatD;
  }
}
