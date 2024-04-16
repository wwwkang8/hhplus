package com.tdd.concert.api.usecase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.tdd.concert.api.controller.dto.response.ConcertResponse;
import com.tdd.concert.domain.concert.component.ConcertManager;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.concert.model.ConcertSchedule;
import com.tdd.concert.domain.concert.repository.ConcertJpaRepository;
import com.tdd.concert.domain.concert.repository.ConcertScheduleJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ConcertDateIntegrationTest {

  @Autowired
  private ConcertDateUseCase concertDateUseCase;

  @Autowired
  private ConcertManager concertManager;

  @Autowired
  private ConcertJpaRepository concertJpaRepository;

  @Autowired
  private ConcertScheduleJpaRepository concertScheduleJpaRepository;

  private Concert concert;
  private List<LocalDate> calendar = new ArrayList<>();
  private ConcertResponse expectedResponse;

  @BeforeEach
  void setUp() {
    concert = concertJpaRepository.save(Concert.builder().name("드림콘서트").singer("아이유").build());

    for (int i = 0; i < 5; i++) {
      concertScheduleJpaRepository.save(
          new ConcertSchedule(LocalDate.now().plusDays(i), concert)
      );
      calendar.add(LocalDate.now().plusDays(i));
    }

    expectedResponse = new ConcertResponse(concert.getConcertId(), calendar);
  }

  @DisplayName("예약가능한 콘서트 날짜 조회")
  @Test
  void case1() {
    // given

    // when
    ConcertResponse actualResponse = concertDateUseCase.availableConcertDate(concert.getConcertId());

    // then
    assertNotNull(actualResponse);
    assertEquals(expectedResponse.getConcertDates().size(), actualResponse.getConcertDates().size());
    assertEquals(expectedResponse.getConcertDates(), actualResponse.getConcertDates());
    assertEquals(expectedResponse.getConcertId(), actualResponse.getConcertId());
  }

  @DisplayName("예약가능한 콘서트 날짜 조회")
  @Test
  void case2() {
    // given
    long fakeConcertId = 999L; // 존재하지 않는 콘서트ID
    List<LocalDate> emptyCalendar = new ArrayList<>();

    // when
    ConcertResponse actualResponse = concertDateUseCase.availableConcertDate(fakeConcertId);

    // then
    assertNotNull(actualResponse);
    assertEquals(0, actualResponse.getConcertDates().size());
    assertEquals(fakeConcertId, actualResponse.getConcertId());
    assertEquals(emptyCalendar, actualResponse.getConcertDates());
  }




}