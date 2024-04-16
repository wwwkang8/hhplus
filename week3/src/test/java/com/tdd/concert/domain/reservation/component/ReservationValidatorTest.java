package com.tdd.concert.domain.reservation.component;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.domain.concert.component.ConcertManager;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import com.tdd.concert.domain.token.component.TokenManager;
import com.tdd.concert.domain.user.component.UserManager;
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
public class ReservationValidatorTest {

  @Mock
  private UserManager userManager;

  @Mock
  private ConcertManager concertManager;

  @Mock
  private TokenManager tokenManager;

  @InjectMocks
  private ReservationValidator reservationValidator;

  private Concert concert;
  private User user;
  private Seat seat;
  private ReservationRequest request = new ReservationRequest();

  @BeforeEach
  void setUp() {
    concert = new Concert(1L, "드림콘서트", "아이유");
    user = new User(1L, "AAAABBBBCCC", 0);
    seat = new Seat(1L, 500, SeatStatus.TEMPORARY_RESERVED);

    request.setSeat(seat);
    request.setConcert(concert);
    request.setUser(user);
  }

  @DisplayName("[예약검증] User가 존재하지 않는 경우 오류")
  @Test
  void case1() {
    // given
    when(userManager.findUserById(anyLong())).thenReturn(null);

    // when, then
    RuntimeException exception = assertThrows(RuntimeException.class, ()->reservationValidator.validate(request));
    assertEquals(exception.getMessage(), "존재하지 않는 사용자입니다.");
  }

  @DisplayName("[예약검증] 콘서트가 존재하지 않는 경우 오류")
  @Test
  void case2() {
    // given
    when(userManager.findUserById(anyLong())).thenReturn(user);
    when(concertManager.findConcertByConcertId(anyLong())).thenReturn(null);

    // when, then
    RuntimeException exception = assertThrows(RuntimeException.class, ()->reservationValidator.validate(request));
    assertEquals(exception.getMessage(), "존재하지 않는 콘서트입니다.");
  }

}
