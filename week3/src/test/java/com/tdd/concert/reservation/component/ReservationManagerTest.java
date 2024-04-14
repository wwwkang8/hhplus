package com.tdd.concert.reservation.component;

import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.reservation.component.ReservationManager;
import com.tdd.concert.domain.reservation.component.ReservationReader;
import com.tdd.concert.domain.reservation.component.ReservationStore;
import com.tdd.concert.domain.reservation.component.ReservationValidator;
import com.tdd.concert.domain.reservation.model.Reservation;
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
import static org.mockito.Mockito.when;

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

  private ReservationRequest request;

  @BeforeEach
  void setUp() {

    request = new ReservationRequest();

    User user = new User(1L, 1500);
    Concert concert = new Concert(1L, "아이유콘서트", "아이유");
    Seat seat = new Seat(1L, 15000, SeatStatus.AVAILABLE);
    request.setUser(user);
    request.setConcert(concert);
    request.setSeat(seat);
  }


  @DisplayName("좌석예약이 정상적으로 실행.")
  @Test
  void case1() {
    // given
    //when(reservationStore.reserveSeat())


    // when
    Reservation actualReservation = reservationManager.reserve(request);


    assertEquals(SeatStatus.TEMPORARY_RESERVED, actualReservation.getSeat().getSeatStatus());
  }

}
