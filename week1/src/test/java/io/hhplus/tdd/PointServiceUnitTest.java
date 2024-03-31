package io.hhplus.tdd;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.PointService;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.apache.catalina.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PointServiceUnitTest {

  @InjectMocks
  private PointService pointService;

  @Mock
  private UserPointTable userPointTable;
  @Mock
  private PointHistoryTable pointHistoryTable;


  @BeforeEach
  void setUp() {
    userPointTable = new UserPointTable();
    pointHistoryTable = new PointHistoryTable();
  }

  @DisplayName("사용자의 포인트 조회")
  @Test
  void 사용자의_포인트_조회() throws InterruptedException {
    // given
    UserPoint userpoint = new UserPoint(1L, 1000L, 10L);
    when(userPointTable.selectById(any())).thenReturn(userpoint);

    // when
    UserPoint userPoint = pointService.getUserPoint(1L);

    //then
    assertEquals(1L, userPoint.id());
    assertEquals(1000L, userPoint.point());
  }

  @DisplayName("사용자가 없는 경우")
  @Test
  void 사용자가_없는_경우() throws InterruptedException {
    //given
    long userId = 2L;
    UserPoint userPoint = new UserPoint(2L, 0L, 10L);
    when(userPointTable.selectById(userId)).thenReturn(userPoint);

    //when
    UserPoint userPoint1 = pointService.getUserPoint(userId);

    //then
    assertEquals(userId, userPoint1.id());
  }

  @DisplayName("사용자아이디가 0L인 경우")
  @Test
  void 사용자_아이디가_0L인_경우() {
    // given
    long userId = 0L;


    // then
    assertThrows(IllegalArgumentException.class, () -> pointService.getUserPoint(userId));
  }

  @DisplayName("사용자 포인트 거래내역 조회")
  @Test
  void 사용자_포인트_거래내역_조회() {
    // given
    long userId = 1L;
    List<PointHistory> expectedPointHistoryList = new ArrayList<>();
    PointHistory pointHistory1 = new PointHistory(1L, 1L, TransactionType.CHARGE, 1000L, 10L);
    PointHistory pointHistory2 = new PointHistory(2L, 1L, TransactionType.CHARGE, 1000L, 10L);
    PointHistory pointHistory3 = new PointHistory(3L, 1L, TransactionType.USE, 500L, 10L);
    expectedPointHistoryList.add(pointHistory1);
    expectedPointHistoryList.add(pointHistory2);
    expectedPointHistoryList.add(pointHistory3);

    when(pointService.getPointHistory(userId)).thenReturn(expectedPointHistoryList);

    // when
    List<PointHistory> actualPointList = pointService.getPointHistory(userId);


    // then
    assertEquals(expectedPointHistoryList.get(0).type(), actualPointList.get(0).type());
    assertEquals(expectedPointHistoryList.get(0).amount(), actualPointList.get(0).amount());
    assertEquals(expectedPointHistoryList.get(1).type(), actualPointList.get(1).type());
    assertEquals(expectedPointHistoryList.get(1).amount(), actualPointList.get(1).amount());
    assertEquals(expectedPointHistoryList.get(2).type(), actualPointList.get(2).type());
    assertEquals(expectedPointHistoryList.get(2).amount(), actualPointList.get(2).amount());
  }

  @DisplayName("사용자의 포인트 거래내역이 없는 경우")
  @Test
  void 사용자의_포인트_거래내역이_없는_경우() {

    // given
    long userId = 1L;
    List<PointHistory> pointHistories = new ArrayList<>();
    when(pointService.getPointHistory(userId)).thenReturn(pointHistories);

    // when
    List<PointHistory> actualPointHistoryList = pointService.getPointHistory(userId);

    // then
    assertEquals(pointHistories, actualPointHistoryList);
  }

  @DisplayName("사용자가 포인트 충전")
  @Test
  void 사용자가_포인트_충전() throws InterruptedException {
    //given
    long userId = 1L;
    UserPoint expectedUserPoint = new UserPoint(userId, 0L, 10L);
    when(pointService.getUserPoint(userId)).thenReturn(expectedUserPoint);

    //when

    UserPoint actualUserPoint = pointService.charge(pointService.getUserPoint(userId).id(), 1000L);

    //then
    assertEquals(actualUserPoint.id(), expectedUserPoint.id());
    assertEquals(actualUserPoint.point(), expectedUserPoint.point());
  }

  @DisplayName("사용자가 0원이하 포인트 충전")
  @Test
  void 사용자가_0원이하_포인트_충전() {
    // given


  }


}
