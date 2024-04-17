package com.tdd.concert.api.usecase;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.tdd.concert.api.controller.dto.response.ConcertResponse;
import com.tdd.concert.domain.concert.component.ConcertManager;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.concert.model.ConcertSchedule;
import com.tdd.concert.domain.concert.repository.ConcertJpaRepository;
import com.tdd.concert.domain.concert.repository.ConcertScheduleJpaRepository;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import com.tdd.concert.domain.seat.repository.SeatJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SeatNoIntegrationTest {

  @Autowired
  private SeatNoUseCase seatNoUseCase;

  @Autowired
  private ConcertManager concertManager;

  @Autowired
  private ConcertJpaRepository concertJpaRepository;

  @Autowired
  private ConcertScheduleJpaRepository concertScheduleJpaRepository;

  @Autowired
  private SeatJpaRepository seatJpaRepository;

  private Concert concert;
  private ConcertSchedule concertSchedule;
  List<Seat> seatList = new ArrayList<>();

  @BeforeEach
  void setUp() {

    // 2. 콘서트 정보 생성
    concert = concertJpaRepository.save(new Concert("드림콘서트", "아이유"));

    // 3. 콘서트 일정 생성
    concertSchedule = concertScheduleJpaRepository.save(new ConcertSchedule(LocalDate.now(), concert));

    long seatNo = 1L;
    // 4. 좌석 5개 생성
    for(int i=0; i<5; i++) {
      Seat seat = Seat.builder()
                      .seatNo(seatNo)
                      .price(1000)
                      .seatStatus(SeatStatus.AVAILABLE)
                      .concert(concert)
                      .concertSchedule(concertSchedule)
                      .tempReservedExpiredAt(null)
                      .tempReservedUserId(null)
                      .build();

      seatList.add(seat);

      seatNo++;
    }
    seatJpaRepository.saveAll(seatList);
  }// @BeforeEach

  @DisplayName("예약가능한 좌석 정보 조회")
  @Test
  void case1() {
    // given

    // when
    ConcertResponse response = seatNoUseCase.seatNoList(concert.getConcertId(), concertSchedule.getConcertDate());

    // then
    assertEquals(concert.getConcertId(), response.getConcertId());
    assertEquals(concertSchedule.getConcertDate(), response.getConcertDate());

    // 좌석번호 배열 비교 검증
    List<Long> seatNoList = new ArrayList<>();
    for(Seat seat : seatList) {
      seatNoList.add(seat.getSeatNo());
    }
    assertEquals(seatNoList,response.getSeatNoList());
  }

  @DisplayName("예약가능한 좌석이 없는 경우")
  @Test
  void case2() {
    // given
    LocalDate concertDate = LocalDate.now().plusDays(5);

    // when : 콘서트가 없는 일자를 파라메터로 넣고 좌석조회 메서드 호출
    ConcertResponse response = seatNoUseCase.seatNoList(concert.getConcertId(), concertDate);

    // then : 좌석번호 리스트는 빈 배열.
    assertEquals(concert.getConcertId(), response.getConcertId());
    assertTrue(response.getSeatNoList().isEmpty());
  }

}
