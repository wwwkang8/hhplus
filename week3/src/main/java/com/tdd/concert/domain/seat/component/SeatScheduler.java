package com.tdd.concert.domain.seat.component;


import java.time.LocalDateTime;
import java.util.List;

import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import com.tdd.concert.domain.seat.repository.SeatCoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class SeatScheduler {

  private final SeatCoreRepository seatCoreRepository;

  @Scheduled(cron = "0 * * * * *")
  @Transactional
  public void updateSeat() {
    // 1. 임시배정시간 5분이 지난 좌석은 임시배정ID, 임시배정만료시각을 공백으로 갱신처리한다. 좌석상태도 AVAILABLE로 변경.
    List<Seat> seatList = seatCoreRepository.findTempReservationExpiredSeatList(SeatStatus.TEMPORARY_RESERVED);

    for(Seat seat : seatList) {

      if(seat.getTempReservedExpiredAt().isBefore(LocalDateTime.now())) {
          seat.expire();
      }
    }
  }

}
