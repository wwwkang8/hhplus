package com.tdd.concert.payment.component;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.payment.component.PaymentManager;
import com.tdd.concert.domain.payment.infra.PaymentCoreRepositoryImpl;
import com.tdd.concert.domain.payment.model.Payment;
import com.tdd.concert.domain.payment.repository.PaymentCoreRepository;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.reservation.model.ReservationStatus;
import com.tdd.concert.domain.seat.model.Seat;
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
public class PaymentManagerTest {

  @Mock
  private PaymentCoreRepositoryImpl paymentCoreRepositoryImpl;

  @InjectMocks
  private PaymentManager paymentManager;

  private Payment payment;

  @BeforeEach
  void setUp() {
    User user = new User(1L, 5000);
    Reservation reservation = new Reservation(1L,
                                              LocalDate.now(),
                                              ReservationStatus.RESERVATION_SUCCESS,
                                              new Concert(),
                                              new User(1L, 5000),
                                              new Seat());
    payment = new Payment(1L, 5000, LocalDateTime.now(), user, reservation);
  }

  @DisplayName("결제내역 저장")
  @Test
  void case1() {
    // given
    when(paymentCoreRepositoryImpl.save(any())).thenReturn(payment);

    // when
    Payment actualPayment = paymentManager.payment(payment);

    // then
    assertEquals(payment, actualPayment);
  }

}
