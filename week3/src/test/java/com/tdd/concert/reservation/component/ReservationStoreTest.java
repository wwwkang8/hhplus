package com.tdd.concert.reservation.component;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.reservation.component.ReservationReader;
import com.tdd.concert.domain.reservation.component.ReservationStore;
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
public class ReservationStoreTest {

  @Mock
  private ReservationCoreRepositoryImpl reservationCoreRepositoryImpl;

  @InjectMocks
  private ReservationStore reservationStore;

  private Concert concert;
  private User user;
  private Seat seat;

  @BeforeEach
  void setUp() {
    concert = new Concert(1L, "드림콘서트", "아이유");
    user = new User(1L, "AAAABBBBCCC", 0);
    seat = new Seat(1L, 500, SeatStatus.SOLDOUT);
  }

  @DisplayName("[예약생성] userId, seatId로 예약내역 조회")
  @Test
  void case1() {
    // given : Reservation 객체 생성 후 저장
    Reservation expectedReservation = Reservation.builder()
        .reservationId(1L)
        .reservationDate(LocalDate.now())
        .reservationStatus(ReservationStatus.RESERVATION_SUCCESS)
        .concert(concert)
        .user(user)
        .seat(seat)
        .build();
    when(reservationCoreRepositoryImpl.reserveSeat(any())).thenReturn(expectedReservation);

    // when : ReservationStore에서 reserveSeat 호출
    Reservation actualReservation = reservationStore.reserveSeat(expectedReservation);

    // then
    assertEquals(expectedReservation, actualReservation);
  }

}
