package com.tdd.concert.api.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import com.tdd.concert.api.controller.dto.request.PointRequest;
import com.tdd.concert.api.controller.dto.response.PointResponse;
import com.tdd.concert.domain.point.component.PointManager;
import com.tdd.concert.domain.point.model.PointRscd;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ChargePointIntegrationTest {

  @Autowired
  private ChargePointUseCase chargePointUseCase;

  @Autowired
  private PointManager pointManager;

  @Autowired
  private UserManager userManager;


  @DisplayName("포인트 충전")
  @Test
  void case1() {
    // given
    User user = userManager.createUser();
    PointRequest pointRequest = new PointRequest(user.getUserId(), 5000, PointRscd.RECEIVE);

    // when
    PointResponse response = chargePointUseCase.charge(pointRequest);


    //then
    assertNotNull(response);
    assertEquals(user.getUserId(), response.getUserId());
    assertEquals(5000, response.getPoint());
  }

  @DisplayName("포인트를 0원 충전하면 오류발생")
  @Test
  void case2() {
    // given
    User user = userManager.createUser();
    PointRequest pointRequest = new PointRequest(user.getUserId(), 0, PointRscd.RECEIVE);


    // when, then
    RuntimeException exception = assertThrows(RuntimeException.class, ()->chargePointUseCase.charge(pointRequest));
    assertEquals("0원 이하는 충전할 수 없습니다.", exception.getMessage());
  }

  @DisplayName("포인트가 음수인 경우 오류발생")
  @Test
  void case3() {
    // given
    User user = userManager.createUser();
    PointRequest pointRequest = new PointRequest(user.getUserId(), -1000, PointRscd.RECEIVE);


    // when, then
    RuntimeException exception = assertThrows(RuntimeException.class, ()->chargePointUseCase.charge(pointRequest));
    assertEquals("0원 이하는 충전할 수 없습니다.", exception.getMessage());
  }

  @DisplayName("포인트 충전 동시성 테스트")
  @Test
  void case4() throws InterruptedException {
    // given
    User user = userManager.createUser();
    int point = 100;
    PointRequest pointRequest = new PointRequest(user.getUserId(), point, PointRscd.RECEIVE);

    // when
    AtomicInteger successCnt = new AtomicInteger(0);
    AtomicInteger failCnt = new AtomicInteger(0);

    final int threadCount = 200;
    final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

    for(int i = 0; i < threadCount; i++) {
      new Thread(() -> {
        try {
          chargePointUseCase.charge(pointRequest);
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

    User actualUser = userManager.findUserById(user.getUserId());
    int totalCount = pointManager.countPointHistoriesByUserId(PointRscd.RECEIVE);

    // 충전 요청은 모두 성공시켜야 하기 때문에 스레드건수 == 성공건수가 일치해야 한다.
    assertEquals(threadCount, successCnt.intValue());

    // 충전된 금액의 정합성이 보장되어야한다.
    assertEquals(point*threadCount, actualUser.getPoint());

    // 포인트 거래내역이 성공건수와 일치하는지 검증
    assertEquals(totalCount, successCnt.intValue());
  }

}
