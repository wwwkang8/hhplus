package com.tdd.concert.api.concurrency;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.tdd.concert.api.controller.dto.request.ReservationRequest;
import com.tdd.concert.api.controller.dto.request.TokenRequest;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.concert.model.ConcertSchedule;
import com.tdd.concert.domain.concert.repository.ConcertJpaRepository;
import com.tdd.concert.domain.concert.repository.ConcertScheduleJpaRepository;
import com.tdd.concert.domain.seat_distribute.component.SeatReaderD;
import com.tdd.concert.domain.seat_distribute.model.SeatD;
import com.tdd.concert.domain.seat_distribute.model.SeatStatusD;
import com.tdd.concert.domain.seat_distribute.repository.SeatJpaRepositoryD;
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
public class ReservationSeatDistributedLockTest {

  @Autowired
  private ReserveSeatDistributedLock reserveSeatDistributedLock;

  @Autowired
  private UserManager userManager;

  @Autowired
  private TokenManager tokenManager;

  @Autowired
  private ConcertJpaRepository concertJpaRepository;

  @Autowired
  private ConcertScheduleJpaRepository concertScheduleJpaRepository;

  @Autowired
  private SeatJpaRepositoryD seatJpaRepositoryD;

  @Autowired
  private SeatReaderD seatReaderD;

  private User user;
  private Concert concert;
  private ConcertSchedule concertSchedule;
  private SeatD seatD;
  private List<User> userList = new ArrayList<>();
  private List<ReservationRequest> reservationRequestList = new ArrayList<>();
  int threadCnt = 100;


  @BeforeEach
  void setUp() {
    // 1. 콘서트 정보 생성
    concert = concertJpaRepository.save(new Concert("드림콘서트", "아이유"));

    // 2. 콘서트 일정 생성
    concertSchedule = concertScheduleJpaRepository.save(new ConcertSchedule(LocalDate.now(), concert));

    // 3. 좌석정보 생성
    seatD = seatJpaRepositoryD.save(
        SeatD.builder()
            .seatNo(1L)
            .price(1000)
            .seatStatusD(SeatStatusD.AVAILABLE)
            .concertId(concert.getConcertId())
            .concertSchedule(concertSchedule.getConcertDate())
            .tempReservedExpiredAt(null)
            .tempReservedUserId(null)
            .build()
    );

    // 4. 좌석 예약 Request 생성
    TokenRequest tokenRequest = new TokenRequest();

    for(int i =0; i<threadCnt; i++) {
      // 토큰 && 사용자 생성
      TokenResponse tokenResponse = tokenManager.insertQueue(tokenRequest);

      // 사용자의 포인트 충전
      user = userManager.findUserById(tokenResponse.getUserId());
      userManager.chargePoint(user.getUserId(), 5000);

      userList.add(user);

      // 좌석 예약 리퀘스트 객체 생성
      ReservationRequest reservationRequest = new ReservationRequest(
          userList.get(i).getUserId(),
          userList.get(i).getToken(),
          concert.getConcertId(),
          concertSchedule.getConcertDate(),
          seatD
      );
      reservationRequestList.add(reservationRequest);

    }
  }

  @DisplayName("다수의 요청자가 1개의 좌석을 동시에 예약하려고 한다.")
  @Test
  void case1() throws InterruptedException {
    // given


    // when
    ExecutorService service = Executors.newFixedThreadPool(threadCnt);
    List<CompletableFuture<Void>> futures = new ArrayList<>();
    AtomicInteger successCnt = new AtomicInteger(0);
    AtomicInteger failCnt = new AtomicInteger(0);

    for (int i = 0; i < threadCnt; i++) {
      int finalI1 = i;
      CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
        try {
          reserveSeatDistributedLock.reserve(reservationRequestList.get(finalI1));
          successCnt.incrementAndGet();
        } catch (Exception e) {
          System.out.println("[Exception] 예외 발생 : " + e.getMessage());
          failCnt.incrementAndGet();
        }
      }, service);
      futures.add(future);
    }

    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    service.shutdown();

    SeatD actualSeatD = seatJpaRepositoryD.findSeatDBySeatId(seatD.getSeatId());

    assertEquals(1, successCnt.intValue());
    assertEquals(threadCnt - successCnt.intValue(), failCnt.intValue());
    assertEquals(SeatStatusD.TEMPORARY_RESERVED, actualSeatD.getSeatStatusD());
  }

}
