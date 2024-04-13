package com.tdd.concert.concertSeat.component;


import static org.mockito.Mockito.when;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.tdd.concert.domain.concert.component.ConcertReader;
import com.tdd.concert.domain.concert.infra.ConcertCoreRepositoryImpl;
import com.tdd.concert.domain.concert.status.ReservationStatus;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

public class ConcertReaderTest {

  @Mock
  private ConcertCoreRepositoryImpl concertCoreRepository;

  private ConcertReader concertReader;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    concertReader = new ConcertReader(concertCoreRepository);

  }

  @DisplayName("콘서트 예약가능일자 조회하기")
  @Test
  void case1() {

    // given
    List<LocalDate> expectedCalendar = new ArrayList<>();
    expectedCalendar.add(LocalDate.now());
    expectedCalendar.add(LocalDate.now().plusDays(1));
    expectedCalendar.add(LocalDate.now().plusDays(2));
    when(concertCoreRepository.availableConcertDate(1)).thenReturn(expectedCalendar);


    // when
    List<LocalDate> actualCalendar = concertReader.availableConcertDate(1);

    // then
    assertEquals(expectedCalendar, actualCalendar);
  }

  @DisplayName("특정날짜 콘서트의 예약 가능죄석 조회하기")
  @Test
  void case2() {

    // given
    List<Long> expectedSeatNoList = new ArrayList<>();
    expectedSeatNoList.add(1L);
    expectedSeatNoList.add(2L);
    expectedSeatNoList.add(3L);
    when(concertCoreRepository.seatNoList(1, LocalDate.now(), ReservationStatus.AVAILABLE)).thenReturn(expectedSeatNoList);


    // when
    List<Long> actualSeatNoList = concertReader.seatNoList(1, LocalDate.now());

    // then
    assertEquals(expectedSeatNoList, actualSeatNoList);
  }



}