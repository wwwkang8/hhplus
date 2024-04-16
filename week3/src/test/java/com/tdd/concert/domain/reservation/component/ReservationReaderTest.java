package com.tdd.concert.domain.reservation.component;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.reservation.infra.ReservationCoreRepositoryImpl;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.reservation.model.ReservationStatus;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import com.tdd.concert.domain.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReservationReaderTest {

  @Mock
  private ReservationCoreRepositoryImpl reservationCoreRepositoryImpl;

  @InjectMocks
  private ReservationReader reservationReader;

  private Concert concert;
  private User user;
  private Seat seat;

  @BeforeEach
  void setUp() {

    concert = new Concert(1L, "드림콘서트", "아이유");
    user = new User(1L, "AAAABBBBCCC", 0);
    seat = new Seat(1L, 500, SeatStatus.SOLDOUT);

  }

  @DisplayName("[예약조회] userId, seatId로 예약내역 조회")
  @Test
  void case1() {
    // given
    Reservation expectedReservation = Reservation.builder()
                                         .reservationId(1L)
                                         .reservationDate(LocalDate.now())
                                         .reservationStatus(ReservationStatus.RESERVATION_SUCCESS)
                                         .concert(concert)
                                         .user(user)
                                         .seat(seat)
                                         .build();
    when(reservationCoreRepositoryImpl.findReservationByUserIdAndSeatId(anyLong(), anyLong())).thenReturn(expectedReservation);

    // when
    Reservation actualReservation = reservationReader.findReservationByUserIdAndSeatId(anyLong(), anyLong());

    // then
    assertEquals(expectedReservation, actualReservation);
  }


}
