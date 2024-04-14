package com.tdd.concert.seat.component;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import com.tdd.concert.domain.seat.component.SeatReader;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import com.tdd.concert.domain.seat.repository.SeatCoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SeatReaderTest {

  @Mock
  private SeatCoreRepository seatCoreRepository;
  
  @InjectMocks
  private SeatReader seatReader;
  
  
  @DisplayName("좌석번호를 조회한다.")
  @Test
  void case1() {
    // given
    Seat expectedSeat = new Seat(1L, 15000, SeatStatus.AVAILABLE);
    when(seatCoreRepository.findSeatBySeatNoAndConcert(1L, 1L, LocalDate.now())).thenReturn(expectedSeat);

    // when
    Seat actualSeat = seatReader.findSeatBySeatNoAndConcert(1L, 1L, LocalDate.now());

    // then
    assertEquals(expectedSeat, actualSeat);
  }

  @DisplayName("비관적락으로 좌석번호를 조회한다")
  @Test
  void case2() {
    //given
    Seat expectedSeat = new Seat(1L, 15000, SeatStatus.AVAILABLE);
    when(seatCoreRepository.findSeatBySeatNoWithExclusiveLock(1L, 1L, LocalDate.now())).thenReturn(expectedSeat);

    // when
    Seat actualSeat = seatReader.findSeatBySeatNoWithExclusiveLock(1L, 1L, LocalDate.now());

    // then
    assertEquals(expectedSeat, actualSeat);
  }

  @DisplayName("없는 좌석조회시 Null을 리턴한다.")
  @Test
  void case3() {
    //given
    Seat expectedSeat = null;
    when(seatCoreRepository.findSeatBySeatNoWithExclusiveLock(1L, 1L, LocalDate.now())).thenReturn(null);

    // when
    Seat actualSeat = seatReader.findSeatBySeatNoWithExclusiveLock(1L, 1L, LocalDate.now());

    // then
    assertEquals(null, actualSeat);
  }

}
