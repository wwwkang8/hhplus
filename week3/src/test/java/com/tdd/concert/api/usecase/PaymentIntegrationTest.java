package com.tdd.concert.api.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import com.tdd.concert.api.controller.dto.request.PaymentRequest;
import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.api.controller.dto.request.TokenRequest;
import com.tdd.concert.api.controller.dto.response.PaymentResponse;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
import com.tdd.concert.domain.concert.component.ConcertManager;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.concert.model.ConcertSchedule;
import com.tdd.concert.domain.concert.repository.ConcertJpaRepository;
import com.tdd.concert.domain.concert.repository.ConcertScheduleJpaRepository;
import com.tdd.concert.domain.payment.component.PaymentManager;
import com.tdd.concert.domain.reservation.component.ReservationManager;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.reservation.model.ReservationStatus;
import com.tdd.concert.domain.seat.component.SeatManager;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import com.tdd.concert.domain.seat.repository.SeatJpaRepository;
import com.tdd.concert.domain.token.component.TokenManager;
import com.tdd.concert.domain.token.component.TokenValidator;
import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.status.ProgressStatus;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentIntegrationTest {

  @Autowired
  private PaymentUseCase paymentUseCase;

  @Autowired
  private UserManager userManager;

  @Autowired
  private TokenValidator tokenValidator;

  @Autowired
  private TokenManager tokenManager;

  @Autowired
  private ConcertManager concertManager;

  @Autowired
  private SeatManager seatManager;

  @Autowired
  private ReservationManager reservationManager;

  @Autowired
  private PaymentManager paymentManager;

  @Autowired
  private ConcertJpaRepository concertJpaRepository;

  @Autowired
  private ConcertScheduleJpaRepository concertScheduleJpaRepository;

  @Autowired
  private SeatJpaRepository seatJpaRepository;

  @Autowired
  private ReserveSeatUseCase reserveSeatUseCase;

  private User testUser;
  private Concert testConcert;
  private ConcertSchedule testConcertSchedule;
  private Seat testSeat;
  int initialPoint;

  @BeforeEach
  void setUp() {
    // 1. 사용자 ID 및 토큰 생성
    initialPoint = 50000;
    TokenRequest tokenRequest = new TokenRequest();
    TokenResponse tokenResponse = tokenManager.insertQueue(tokenRequest);
    testUser = userManager.findUserById(tokenResponse.getUserId()); // testUser 객체 조회
    userManager.chargePoint(testUser.getUserId(), initialPoint); // 포인트 충전

    // 2. 콘서트, 콘서트 스케쥴 생성
    testConcert = concertJpaRepository.save(new Concert("드림콘서트", "아이유"));
    testConcertSchedule = concertScheduleJpaRepository.save(
                new ConcertSchedule(LocalDate.now(), testConcert)
    );

    // 3. 좌석 생성
    Seat seat1 = Seat.builder()
                    .seatNo(1L)
                    .price(1000)
                    .seatStatus(SeatStatus.AVAILABLE)
                    .tempReservedExpiredAt(null)
                    .tempReservedUserId(null)
                    .concert(testConcert)
                    .concertSchedule(testConcertSchedule)
                    .build();
    testSeat = seatJpaRepository.save(seat1);

    // 4. 좌석 예약
    ReservationRequest reservationRequest = new ReservationRequest(
                                              testUser.getUserId(),
                                              testUser.getToken(),
                                              testConcert.getConcertId(),
                                              testConcertSchedule.getConcertDate(),
                                              testSeat.getSeatNo()
    );

    reserveSeatUseCase.reserve(reservationRequest);
  }

  @DisplayName("예약된 좌석을 결제처리한다.")
  @Transactional
  @Test
  void case1() {
    // given : 결제처리를 위한 Request 객체를 생성한다.(유저ID, 토큰, 콘서트ID, 콘서트일자, 좌석번호 필요)
    PaymentRequest paymentRequest = new PaymentRequest(
        testUser.getUserId(),
        testUser.getToken(),
        testConcert.getConcertId(),
        testConcertSchedule.getConcertDate(),
        testSeat.getSeatNo()
    );

    // when : 결제처리
    PaymentResponse paymentResponse = paymentUseCase.payment(paymentRequest);

    // then : 결제처리 이후 데이터들의 상태 검증
    User actualUser = userManager.findUserById(paymentResponse.getUserId());
    Token actualToken = tokenManager.findTokenByToken(actualUser.getToken());
    Seat actualSeat = seatManager.findSeatBySeatNoAndConcert(testSeat.getSeatNo(),
                                                             testConcert.getConcertId(),
                                                             testConcertSchedule.getConcertDate());
    Reservation actualReservation = reservationManager.findReservationByUserIdAndSeatId(testUser.getUserId(), testSeat.getSeatId());
    int remainPoint = initialPoint - testSeat.getPrice(); // 포인트 잔액

    assertEquals(ProgressStatus.FINISHED, actualToken.getProgressStatus());
    assertEquals(remainPoint, testUser.getPoint());
    assertEquals(ReservationStatus.RESERVATION_SUCCESS, actualReservation.getReservationStatus());
    assertEquals(testUser.getUserId(), actualReservation.getUser().getUserId());
    assertEquals(LocalDate.now(), actualReservation.getReservationDate());
    assertEquals(testConcert.getConcertId(), actualReservation.getConcert().getConcertId());
    assertEquals(testSeat.getSeatNo(), actualReservation.getSeat().getSeatNo());
    assertEquals(SeatStatus.SOLDOUT, actualSeat.getSeatStatus());
  }

  @DisplayName("만료시각이 지나서 사용자의 토큰이 만료된 경우 오류발생")
  @Transactional
  @Test
  void case2() {
    // given : 사용자의 토큰을 만료시켜버린다.
    Token token = tokenManager.findTokenByToken(testUser.getToken());
    token.dropToken(); // 사용자의 토큰을 만료시켜버리는 메서드

    PaymentRequest paymentRequest = new PaymentRequest(
        testUser.getUserId(),
        testUser.getToken(),
        testConcert.getConcertId(),
        testConcertSchedule.getConcertDate(),
        testSeat.getSeatNo()
    );

    // when : 결제처리

    // then : 결제처리 이후 데이터들의 상태 검증
    RuntimeException exception = assertThrows(RuntimeException.class, ()->paymentUseCase.payment(paymentRequest));
    String expectedMessage = "만료된 토큰입니다. 토큰 : " + token.getToken() + ", 토큰 상태 : " + token.getProgressStatus();
    assertEquals(expectedMessage, exception.getMessage());
  }

  @DisplayName("이미 예약을 완료하여 토큰이 만료된 경우 오류 발생")
  @Transactional
  @Test
  void case3() {
    // given : 사용자의 토큰을 만료시켜버린다.
    Token token = tokenManager.findTokenByToken(testUser.getToken());
    token.expireToken(); // 사용자의 토큰을 만료시켜버리는 메서드

    PaymentRequest paymentRequest = new PaymentRequest(
        testUser.getUserId(),
        testUser.getToken(),
        testConcert.getConcertId(),
        testConcertSchedule.getConcertDate(),
        testSeat.getSeatNo()
    );

    // when : 결제처리

    // then : 결제처리 이후 데이터들의 상태 검증
    RuntimeException exception = assertThrows(RuntimeException.class, ()->paymentUseCase.payment(paymentRequest));
    String expectedMessage = "예약이 완료된 토큰입니다. 토큰 : " + token.getToken() + ", 토큰 상태 : " + token.getProgressStatus();
    assertEquals(expectedMessage, exception.getMessage());
  }

  @DisplayName("토큰이 존재하지 않는 경우 오류발생.")
  @Transactional
  @Test
  void case4() {
    // given : 토큰이 없는 사용자를 생성한다
    User fakeUser = userManager.createUser();
    PaymentRequest paymentRequest = new PaymentRequest(
        fakeUser.getUserId(),
        fakeUser.getToken(),
        testConcert.getConcertId(),
        testConcertSchedule.getConcertDate(),
        testSeat.getSeatNo()
    );

    // when, then : 결제처리 이후 데이터들의 상태 검증
    RuntimeException exception = assertThrows(RuntimeException.class, ()->paymentUseCase.payment(paymentRequest));
    String expectedMessage = "대기열에 존재하지 않는 토큰입니다. 토큰 : " + fakeUser.getToken();
    assertEquals(expectedMessage, exception.getMessage());
  }

  @DisplayName("임시배정이 되지 않은 좌석인 경우 예약 불가")
  @Transactional
  @Test
  void case5() {
    // given : 좌석을 AVAILABLE 상태로 변경한다
    testSeat.setSeatStatus(SeatStatus.AVAILABLE);
    PaymentRequest paymentRequest = new PaymentRequest(
        testUser.getUserId(),
        testUser.getToken(),
        testConcert.getConcertId(),
        testConcertSchedule.getConcertDate(),
        testSeat.getSeatNo()
    );

    // when, then : 결제처리 이후 데이터들의 상태 검증
    RuntimeException exception = assertThrows(RuntimeException.class, ()->paymentUseCase.payment(paymentRequest));
    String expectedMessage = "[PaymentUseCase] 임시배정상태가 아닌 좌석입니다. 좌석 예약을 먼저 해주세요.";
    assertEquals(expectedMessage, exception.getMessage());
  }

  @DisplayName("이미 판매가 완료된 좌석의 경우 오류 발생")
  @Transactional
  @Test
  void case6() {
    // given : 좌석을 판매 완료 상태로 변경한다.
    testSeat.setSeatStatus(SeatStatus.SOLDOUT);
    PaymentRequest paymentRequest = new PaymentRequest(
        testUser.getUserId(),
        testUser.getToken(),
        testConcert.getConcertId(),
        testConcertSchedule.getConcertDate(),
        testSeat.getSeatNo()
    );

    // when, then : 결제처리 이후 데이터들의 상태 검증
    RuntimeException exception = assertThrows(RuntimeException.class, ()->paymentUseCase.payment(paymentRequest));
    String expectedMessage = "[PaymentUseCase] 이미 판매가 완료된 좌석입니다. 다른 좌석을 선택해주세요.";
    assertEquals(expectedMessage, exception.getMessage());
  }

  @DisplayName("임시배정한 사용자ID와 결제요청한 사용자ID가 다른 경우 오류발생.")
  @Transactional
  @Test
  void case7() {
    // given : 임시배정사용자 아이디를 변경한다.
    testSeat.setTempReservedUserId(999L);
    PaymentRequest paymentRequest = new PaymentRequest(
        testUser.getUserId(),
        testUser.getToken(),
        testConcert.getConcertId(),
        testConcertSchedule.getConcertDate(),
        testSeat.getSeatNo()
    );

    // when, then : 결제처리 이후 데이터들의 상태 검증
    RuntimeException exception = assertThrows(RuntimeException.class, ()->paymentUseCase.payment(paymentRequest));
    String expectedMessage = "[PaymentUseCase] 임시배정된 사용자ID와 결제 요청한 사용자ID가 다릅니다.";
    assertEquals(expectedMessage, exception.getMessage());
  }


}
