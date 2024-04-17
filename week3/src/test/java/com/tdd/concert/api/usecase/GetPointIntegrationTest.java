package com.tdd.concert.api.usecase;

import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GetPointIntegrationTest {

  @Autowired
  private GetPointUseCase getPointUseCase;

  @Autowired
  private UserManager userManager;

  private User user;

  @BeforeEach
  void setUp() {
    // 사용자 생성 -> 포인트 잔액 5000원 세팅
    user = userManager.createUser();
  }


  @DisplayName("유저의 포인트를 조회한다")
  @Test
  void case1() {
    // given : 사용자에게 5000원 포인트 추가
    User expectedUser = userManager.chargePoint(user.getUserId(), 5000);


    // when
    User actualUser = userManager.findUserById(expectedUser.getUserId());

    // then
    assertEquals(expectedUser.getUserId(), actualUser.getUserId());
    assertEquals(expectedUser.getPoint(), actualUser.getPoint());
  }

  @DisplayName("존재하지 않는 유저의 포인트 조회")
  @Test
  void case2() {
    // given : 생성되지 않는 사용자 ID로 조회
    User fakeUser = new User(999L, 0);

    // when
    User actualUser = userManager.findUserById(fakeUser.getUserId());

    // then
    assertNull(actualUser);
  }

}
