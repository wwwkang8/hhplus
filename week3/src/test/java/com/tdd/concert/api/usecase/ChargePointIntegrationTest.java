package com.tdd.concert.api.usecase;

import com.tdd.concert.api.controller.dto.request.PointRequest;
import com.tdd.concert.api.controller.dto.response.PointResponse;
import com.tdd.concert.domain.point.component.PointManager;
import com.tdd.concert.domain.point.model.PointHistory;
import com.tdd.concert.domain.point.model.PointRscd;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

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

}
