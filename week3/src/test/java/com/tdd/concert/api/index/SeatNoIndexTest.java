package com.tdd.concert.api.index;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Optional;

import com.tdd.concert.api.usecase.SeatNoUseCase;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.concert.model.ConcertSchedule;
import com.tdd.concert.domain.concert.repository.ConcertJpaRepository;
import com.tdd.concert.domain.concert.repository.ConcertScheduleJpaRepository;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.repository.SeatJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class SeatNoIndexTest {

  @Autowired
  private SeatNoUseCase seatNoUseCase;

  @Autowired
  private ConcertJpaRepository concertJpaRepository;

  @Autowired
  private ConcertScheduleJpaRepository concertScheduleJpaRepository;

  @Autowired
  private SeatJpaRepository seatJpaRepository;

  private Concert concert;
  private Optional<ConcertSchedule> concertSchedule;
  long concertId;
  long concertScheduleId;
  LocalDate concertDate;

  @BeforeEach
  void setUp() {
    concertId = concertJpaRepository.findConcertByConcertId(1L).getConcertId();
    concertSchedule = concertScheduleJpaRepository.findById(1L);
    concertScheduleId = concertSchedule.get().getConcertScheduleId();
    concertDate = concertSchedule.get().getConcertDate();
  }


  @Test
  void case1() {
    // given
    long seatNo = 61101L; // 검색하고자 하는 좌석 번호

    // when
    Seat seat = seatJpaRepository.findSeatBySeatNoAndConcert(
        seatNo,
        concertId,
        concertDate
    );


    //then
    assertEquals(seatNo, seat.getSeatNo());
  }

  @Test
  void case2() {
    // given
    long seatNo = 61101L; // 검색하고자 하는 좌석 번호

    // when
    Seat seat = seatJpaRepository.findSeatBySeatNoAndConcertAndConcertSchedule(
        seatNo,
        concertId,
        concertScheduleId
    );


    //then
    assertEquals(seatNo, seat.getSeatNo());
  }




































}
