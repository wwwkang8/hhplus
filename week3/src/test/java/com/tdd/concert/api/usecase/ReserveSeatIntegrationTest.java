package com.tdd.concert.api.usecase;

import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.api.controller.dto.request.TokenRequest;
import com.tdd.concert.api.controller.dto.response.ReservationResponse;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
import com.tdd.concert.domain.concert.component.ConcertManager;
import com.tdd.concert.domain.concert.infra.ConcertCoreRepositoryImpl;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.concert.model.ConcertSchedule;
import com.tdd.concert.domain.concert.repository.ConcertJpaRepository;
import com.tdd.concert.domain.concert.repository.ConcertScheduleJpaRepository;
import com.tdd.concert.domain.reservation.component.ReservationManager;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.reservation.model.ReservationStatus;
import com.tdd.concert.domain.seat.component.SeatManager;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.seat.model.SeatStatus;
import com.tdd.concert.domain.seat.repository.SeatJpaRepository;
import com.tdd.concert.domain.token.component.TokenManager;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@Slf4j
public class ReserveSeatIntegrationTest {

  @Autowired
  private ReserveSeatUseCase reserveSeatUseCase;

  @Autowired
  private UserManager userManager;

  @Autowired
  private TokenManager tokenManager;

  @Autowired
  private ConcertJpaRepository concertJpaRepository;

  @Autowired
  private ConcertScheduleJpaRepository concertScheduleJpaRepository;

  @Autowired
  private SeatJpaRepository seatJpaRepository;

  private User user;
  private Concert concert;
  private ConcertSchedule concertSchedule;
  private Seat seat;


  @BeforeEach
  void setUp() {

    // 1. 토큰 생성 및 유저 정보 생성
    TokenRequest tokenRequest = new TokenRequest();
    TokenResponse tokenResponse =  tokenManager.insertQueue(tokenRequest);
    user = userManager.findUserById(tokenResponse.getUserId());

    // 2. 콘서트 정보 생성
    concert = concertJpaRepository.save(new Concert("드림콘서트", "아이유"));

    // 3. 콘서트 일정 생성
    concertSchedule = concertScheduleJpaRepository.save(new ConcertSchedule(LocalDate.now(), concert));

    // 4. 좌석정보 생성
    seat = seatJpaRepository.save(
        Seat.builder()
            .seatNo(20L)
            .price(1000)
            .seatStatus(SeatStatus.AVAILABLE)
            .concert(concert)
            .concertSchedule(concertSchedule)
            .tempReservedExpiredAt(null)
            .tempReservedUserId(null)
            .build()
    );
  }

  @DisplayName("사용자가 좌석을 예약한다")
  @Test
  void case1() {
    // given
    // 미리 생성된 사용자의 포인트를 5000원 충전한다.
    User actualUser = userManager.chargePoint(user.getUserId(), 5000);

    // 좌석예약에 필요한 request 객체 생성(사용자, 사용자토큰, 콘서트, 콘서트 날짜, 좌석 정보를 넣고 객체 생성)
    ReservationRequest reservationRequest = new ReservationRequest( actualUser.getUserId(),
                                                                    actualUser.getToken(),
                                                                    concert.getConcertId(),
                                                                    concertSchedule.getConcertDate(),
                                                                    seat.getSeatNo()
                                                                );

    // when
    ReservationResponse actualResponse = reserveSeatUseCase.reserve(reservationRequest);

    // then
    assertNotNull(actualResponse);
    assertEquals(20L, actualResponse.getSeatNo());
    assertEquals(actualUser.getUserId(), actualResponse.getUserId());

    // DateTimeFormatter를 사용하여 원하는 형식의 문자열로 변환
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    String expectedTime = LocalDateTime.now().plusMinutes(5).format(formatter);
    String actualTime = actualResponse.getExpiredAt().format(formatter);

    // 좌석 임시배정시각을 검증
    assertEquals(expectedTime, actualTime);
  }


  @DisplayName("다수의 요청자가 1개의 좌석을 동시에 예약하려고 한다.")
  @Disabled
  @Test
  void case4() throws InterruptedException {
    // given


    // when
    final int threadCount = 5;
    final ExecutorService executorService = Executors.newFixedThreadPool(100);
    final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

    for(int i=0; i<threadCount; i++) {
      TokenRequest tokenRequest = new TokenRequest();

      // 토큰 && 사용자 생성
      TokenResponse tokenResponse =  tokenManager.insertQueue(tokenRequest);

      // 사용자의 포인트 충전
      user = userManager.findUserById(tokenResponse.getUserId());
      userManager.chargePoint(user.getUserId(), 5000);

      // 좌석 예약 리퀘스트 객체 생성
      ReservationRequest reservationRequest = new ReservationRequest(user.getUserId(),
                                                                      user.getToken(),
                                                                      concert.getConcertId(),
                                                                      concertSchedule.getConcertDate(),
                                                                      seat
                                                                  );

      executorService.submit(()->{
        try{
          // 좌석 예약 시작
          reserveSeatUseCase.reserve(reservationRequest);
        }finally{
          countDownLatch.countDown();
        }
      });
      countDownLatch.await();
    }

    Seat actualSeat = seatJpaRepository.findSeatBySeatNoAndConcert(seat.getSeatNo(), concert.getConcertId(), concertSchedule.getConcertDate());
    assertEquals(SeatStatus.TEMPORARY_RESERVED, actualSeat.getSeatStatus());
  }
































}
