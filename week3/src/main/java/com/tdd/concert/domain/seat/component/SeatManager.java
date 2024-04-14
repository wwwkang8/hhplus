package com.tdd.concert.domain.seat.component;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import com.tdd.concert.domain.seat.repository.SeatCoreRepository;
import com.tdd.concert.domain.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeatManager {

  private final SeatValidator seatValidator;
  private final SeatCoreRepository seatCoreRepository;

  /** 좌석번호를 조회하는 메서드. */
  public Seat findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate) {
    return seatCoreRepository.findSeatBySeatNoAndConcert(seatNo, concertId, concertDate);
  }

  /** 비관적락으로 좌석을 조회하는 메서드
   * 좌석예약시에만 활용한다. */
  public Seat findSeatBySeatNoWithExclusiveLock(Long seatNo, Long concertId, LocalDate concertDate) {
    return seatCoreRepository.findSeatBySeatNoWithExclusiveLock(seatNo, concertId, concertDate);
  }

  @Transactional
  public Seat occupy(Long seatNo, Long concertId, LocalDate concertDate, User user) {

    /** 비관적락으로 Seat 조회 */
    Seat seat = findSeatBySeatNoWithExclusiveLock(seatNo, concertId, concertDate);

    /** 좌석 검증처리 */
    seatValidator.validate(seat);

    /** 좌석의 임시배정만료시각, 임시배정 사용자아이디, 좌석예약상태를 설정 후 저장. */
    seat.tempOccupy(user.getUserId());

    return seatCoreRepository.save(seat);
  }
}
