package com.tdd.concert.api.concurrency;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.api.controller.dto.request.TokenRequest;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.concert.model.ConcertSchedule;
import com.tdd.concert.domain.concert.repository.ConcertJpaRepository;
import com.tdd.concert.domain.concert.repository.ConcertScheduleJpaRepository;
import com.tdd.concert.domain.seat_optimistic.component.SeatReaderO;
import com.tdd.concert.domain.seat_optimistic.model.SeatO;
import com.tdd.concert.domain.seat_optimistic.model.SeatStatusO;
import com.tdd.concert.domain.seat_optimistic.repository.SeatJpaRepositoryO;
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
public class ReservationSeatOptimisticLockTest {

  @Autowired
  private ReserveSeatOptimisticLock reservationSeatOptimisticLock;

  @Autowired
  private UserManager userManager;

  @Autowired
  private TokenManager tokenManager;

  @Autowired
  private ConcertJpaRepository concertJpaRepository;

  @Autowired
  private ConcertScheduleJpaRepository concertScheduleJpaRepository;

  @Autowired
  private SeatJpaRepositoryO seatJpaRepositoryO;

  @Autowired
  private SeatReaderO seatReaderO;

  private User user;
  private Concert concert;
  private ConcertSchedule concertSchedule;
  private SeatO seatO;


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
    seatO = seatJpaRepositoryO.save(
        SeatO.builder()
            .seatNo(1L)
            .price(1000)
            .seatStatusO(SeatStatusO.AVAILABLE)
            .concert(concert)
            .concertSchedule(concertSchedule)
            .tempReservedExpiredAt(null)
            .tempReservedUserId(null)
            .version(0L)
            .build()
    );
  }


  @DisplayName("다수의 요청자가 1개의 좌석을 동시에 예약하려고 한다.")
  @Test
  void case1() throws InterruptedException {
    // given


    // when
     AtomicInteger successCnt = new AtomicInteger(0);
     AtomicInteger failCnt = new AtomicInteger(0);

    final int threadCount = 100;
    final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

    for(int i = 0; i < threadCount; i++) {
      new Thread(() -> {
        try {
          // 좌석 예약 시작
          TokenRequest tokenRequest = new TokenRequest();

          // 토큰 && 사용자 생성
          TokenResponse tokenResponse = tokenManager.insertQueue(tokenRequest);

          // 사용자의 포인트 충전
          user = userManager.findUserById(tokenResponse.getUserId());
          userManager.chargePoint(user.getUserId(), 5000);

          // 좌석 조회
          // 좌석은 setUp에서 생성

          // 좌석 예약 리퀘스트 객체 생성
          ReservationRequest reservationRequest = new ReservationRequest(
              user.getUserId(),
              user.getToken(),
              concert.getConcertId(),
              concertSchedule.getConcertDate(),
              seatO
          );
          reservationSeatOptimisticLock.reserve(reservationRequest);
          successCnt.incrementAndGet();
        } catch(Exception e) {
          System.out.println("Exception message : " + e.getMessage());
          failCnt.incrementAndGet();
        }
        finally {
          countDownLatch.countDown();
        }
      }).start();
    }
    countDownLatch.await();

    Thread.sleep(1000);

    SeatO actualSeatO = seatJpaRepositoryO.findSeatOBySeatId(seatO.getSeatId());

    assertEquals(threadCount-failCnt.intValue(), successCnt.intValue());

    // version은 성공한 횟수만큼 증가
    assertEquals(1, actualSeatO.getVersion());
  }



}
