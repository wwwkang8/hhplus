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
  private final SeatReader seatReader;

  public Seat findSeatBySeatNoAndConcert(Long seatNo, Long concertId, LocalDate concertDate) {
    return seatReader.findSeatBySeatNoAndConcert(seatNo, concertId, concertDate);
  }

  public Seat occupy(Long seatNo, Long concertId, LocalDate concertDate, User user) {
    log.info("[SeatManager] 좌석 occupy 메서드 진입");

    /** 비관적락으로 Seat 조회 */
    Seat seat = seatReader.findSeatBySeatNoWithExclusiveLock(seatNo, concertId, concertDate);
    log.info("[SeatManager] 좌석 비관적락으로 조회 완료");


    /** 좌석 검증처리 */
    seatValidator.validate(seat);

    log.info("[SeatManager] 좌석 검증처리 완료");

    /** 좌석의 임시배정만료시각, 임시배정 사용자아이디, 좌석예약상태를 설정 후 저장. */
    seat.tempOccupy(user.getUserId());

    log.info("[SeatManager] 좌석 tempOccupy 완료");

    // return seatCoreRepository.save(seat);
    return seat;
  }
}
