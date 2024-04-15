package com.tdd.concert.reservation.component;

import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.reservation.component.ReservationManager;
import com.tdd.concert.domain.reservation.component.ReservationReader;
import com.tdd.concert.domain.reservation.component.ReservationStore;
import com.tdd.concert.domain.reservation.component.ReservationValidator;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class ReservationManagerTest {

  @Mock
  private ReservationReader reservationReader;

  @Mock
  private ReservationStore reservationStore;

  @Mock
  private ReservationValidator reservationValidator;

  @InjectMocks
  private ReservationManager reservationManager;

  private Concert concert;
  private User user;
  private Seat seat;
  private ReservationRequest request = new ReservationRequest();
  private Reservation expectedReservation;

  @BeforeEach
  void setUp() {
    concert = new Concert(1L, "드림콘서트", "아이유");
    user = new User(1L, "AAAABBBBCCC", 0);
    seat = new Seat(1L, 500, SeatStatus.TEMPORARY_RESERVED);

    request.setSeat(seat);
    request.setConcert(concert);
    request.setUser(user);

    expectedReservation = Reservation.builder()
                          .reservationId(1L)
                          .reservationDate(LocalDate.now())
                          .reservationStatus(ReservationStatus.TEMPORARY_RESERVED)
                          .concert(concert)
                          .user(user)
                          .seat(seat)
                          .build();
  }


  @DisplayName("[좌석예약처리] 좌석예약이 정상적으로 실행.")
  @Test
  void case1() {
    // given
    when(reservationStore.reserveSeat(any())).thenReturn(expectedReservation);

    // when
    Reservation actualReservation = reservationManager.reserve(request);

    // then
    assertEquals(SeatStatus.TEMPORARY_RESERVED, actualReservation.getSeat().getSeatStatus());
    assertEquals(ReservationStatus.TEMPORARY_RESERVED, actualReservation.getReservationStatus());
    assertEquals(expectedReservation.getReservationId(), actualReservation.getReservationId());
    verify(reservationValidator).validate(request);
  }

  @DisplayName("[좌석예약처리2] 좌석예약시 상태는 임시배정상태")
  @Test
  void case2() {
    // given
    when(reservationStore.reserveSeat(any())).thenReturn(expectedReservation);

    // when
    Reservation actualReservation = reservationManager.reserve(request);

    // then
    assertNotEquals(ReservationStatus.RESERVATION_SUCCESS, actualReservation.getReservationStatus());
  }

}
