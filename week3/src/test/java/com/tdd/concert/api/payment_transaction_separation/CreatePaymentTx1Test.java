package com.tdd.concert.api.payment_transaction_separation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import com.tdd.concert.api.controller.dto.request.PaymentRequest;
import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.api.controller.dto.request.TokenRequest;
import com.tdd.concert.api.controller.dto.response.PaymentResponse;
import com.tdd.concert.api.controller.dto.response.ReservationResponse;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
import com.tdd.concert.api.controller.mockapi.PaymentResult;
import com.tdd.concert.api.payment_transaction_separation.tx1.CreatePayment_Tx1;
import com.tdd.concert.api.usecase.ReserveSeatUseCase;
import com.tdd.concert.domain.outbox.component.OutboxManager;
import com.tdd.concert.domain.outbox.component.PayLoadGenerator;
import com.tdd.concert.domain.token.component.TokenManager;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class CreatePaymentTx1Test {

  @Autowired
  private CreatePayment_Tx1 createPaymentTx1;

  @Autowired
  private UserManager userManager;

  @Autowired
  private TokenManager tokenManager;

  @Autowired
  private ReserveSeatUseCase reserveSeatUseCase;


  private User testUser;
  Long concertId = 1L;
  Long concertScheduleId = 1L;
  Long seatNo = 1L;
  LocalDate concertSchedule = LocalDate.parse("2024-05-01");
  ReservationResponse reservationResponse;
  int initialPoint;

  @BeforeEach
  void setUp() {
    // 1. 사용자 ID 및 토큰 생성
    initialPoint = 50000;
    TokenRequest tokenRequest = new TokenRequest();
    TokenResponse tokenResponse = tokenManager.insertQueue(tokenRequest);
    testUser = userManager.findUserById(tokenResponse.getUserId()); // testUser 객체 조회
    userManager.chargePoint(testUser.getUserId(), initialPoint); // 포인트 충전

    // 4. 좌석 예약
    ReservationRequest reservationRequest = new ReservationRequest(
        testUser.getUserId(),
        testUser.getToken(),
        concertId,
        concertSchedule,
        seatNo
    );

    reservationResponse = reserveSeatUseCase.reserve(reservationRequest);
  }

  @Test
  @DisplayName("결제 트랜잭션이 완료된 이후에 이벤트 리스너 테스트")
  void case1() {
    // given
    PaymentRequest request = new PaymentRequest(testUser.getUserId(), testUser.getToken(), concertId, concertSchedule, seatNo);

    // when
    PaymentResponse actualResponse = createPaymentTx1.payment(request);

    // then
    assertEquals(testUser.getUserId(), actualResponse.getUserId());
    assertEquals(testUser.getToken(), actualResponse.getToken());
    assertEquals(concertId, actualResponse.getConcertId());
    assertEquals(concertSchedule, actualResponse.getConcertDate());
    assertEquals(PaymentResult.SUCCESS, actualResponse.getPaymentResult());
  }

  @Test
  @DisplayName("Outbox 테이블에 이벤트 데이터 INSERT 테스트")
  void case2() {
    // given
//    PaymentRequest request = new PaymentRequest(testUser.getUserId(), testUser.getToken(), concertId, concertSchedule, seatNo);
//    createPaymentTx1.payment(request);
//    String expectedPayLoad = payLoadGenerator.generatePayload(testUser.getUserId(),
//                                                              reservationResponse.ge)
//
//
//    // when
//    Outbox outbox = outboxManager.findOutboxById(1L);
//
//    // then
//    assertEquals();


  }





























}
