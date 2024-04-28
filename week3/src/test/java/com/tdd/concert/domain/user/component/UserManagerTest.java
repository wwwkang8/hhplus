package com.tdd.concert.domain.user.component;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
public class UserManagerTest {

  @Mock
  private UserReader userReader;

  @Mock
  private UserStore userStore;

  @InjectMocks
  private UserManagerImpl userManagerImpl;

  private User user;

  @BeforeEach
  void setUp() {
    user = new User(1L, 0);
  }

  @DisplayName("포인트 충전하다")
  @Test
  void case1() {
    // given : 사용자 조회
    when(userReader.findUserById(any())).thenReturn(user);

    // when : 사용자 포인트 충전
    User actualUser = userManagerImpl.chargePoint(user.getUserId(), 5000);

    // then
    assertEquals(1L, actualUser.getUserId());
    assertEquals(5000, actualUser.getPoint());
  }

  @DisplayName("0원을 충전하는 경우 오류 발생")
  @Test
  void case2() {
    // given

    // when : 사용자 포인트 충전
    RuntimeException exception = assertThrows(RuntimeException.class, ()->userManagerImpl.chargePoint(user.getUserId(), 0));
    String expectedMessage = "0원 이하는 충전할 수 없습니다.";

    // then
    assertEquals(expectedMessage, exception.getMessage());
  }

  @DisplayName("포인트 사용하다.")
  @Test
  void case3() {
    // given : 사용자 조회
    user.setPoint(5000); // 초기 포인트 잔액 설정
    when(userReader.findUserById(any())).thenReturn(user);

    // when : 사용자 포인트 충전
    User actualUser = userManagerImpl.usePoint(user.getUserId(), 3000);

    // then
    assertEquals(1L, actualUser.getUserId());
    assertEquals(2000, actualUser.getPoint());
  }

  @DisplayName("현재 잔액보다 많은 포인트를 사용하는 경우 오류발생.")
  @Test
  void case4() {
    // given : 사용자 조회
    user.setPoint(5000); // 초기 포인트 잔액 설정
    when(userReader.findUserById(any())).thenReturn(user);

    // when : 사용자 포인트 충전
    RuntimeException exception = assertThrows(RuntimeException.class, ()->userManagerImpl.usePoint(user.getUserId(), 8000));
    String expectedMessage = "잔액이 부족합니다." + " 사용자ID : " + user.getUserId() + ", 잔액:" + user.getPoint();

    // then
    assertEquals(expectedMessage, exception.getMessage());
  }

  @DisplayName("0원미만의 포인트를 사용하는 경우 오류발생.")
  @Test
  void case5() {
    // given : 사용자 조회
    user.setPoint(5000); // 초기 포인트 잔액 설정
    when(userReader.findUserById(any())).thenReturn(user);

    // when : 사용자 포인트 충전
    RuntimeException exception = assertThrows(RuntimeException.class, ()->userManagerImpl.usePoint(user.getUserId(), 0));
    String expectedMessage = "0원 이하는 사용할 수 없습니다.";

    // then
    assertEquals(expectedMessage, exception.getMessage());
  }














}
