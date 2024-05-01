package com.tdd.concert.domain.seat.component;

import com.tdd.concert.domain.seat.repository.SeatCoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SeatManagerTest {

  @Mock
  private SeatCoreRepository seatCoreRepository;

  @Mock
  private SeatValidator seatValidator;

  @Mock
  private SeatReader seatReader;

  @InjectMocks
  private SeatManager seatManager;


  @DisplayName("좌석을 임시배정한다.")
  @Test
  void case1() {
    // given
//    Long seatNo = 1L;
//    Long concertId = 1L;
//    LocalDate concertDate = LocalDate.now();
//    Seat expectedSeat = new Seat(1L, 15000, SeatStatus.AVAILABLE);
//    when(seatReader.findSeatBySeatNoWithExclusiveLock(seatNo, concertId, concertDate)).thenReturn(expectedSeat);
//    User user = new User(1L, 50000);
//
//    // when
//    Seat actualSeat = seatManager.occupy(seatNo, concertId, concertDate, user);
//
//
//    // then
//    assertEquals(expectedSeat, actualSeat);
//    verify(seatValidator).validate(expectedSeat);
  }

}
