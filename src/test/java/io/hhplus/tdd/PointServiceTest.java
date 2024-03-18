package io.hhplus.tdd;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.PointService;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PointServiceTest {

  @Autowired
  private UserPointTable userPointTable;

  @Autowired
  private  PointHistoryTable pointHistoryTable;

  @Autowired
  private  PointService pointService;


  @Test
  void 사용자_포인트_조회() throws InterruptedException {
    // given
    long userId = 1L;
    userPointTable.insertOrUpdate(userId, 5000L);

    // when
    //UserPoint userPoint = userPointTable.selectById(userId);
    UserPoint userPoint = pointService.getUserPoint(userId);

    // then
    assertEquals(userPoint.id(), userId);
  }

  /** 테스트케이스 : 사용자가 없는 경우 */
  @Test
  void 사용자가_없는_경우() throws InterruptedException {
    /** 사용자가 없는 경우 userId 넘겨주면, 새로운 사용자 인스턴스 반환 */
    //given
    long userId = 2L;

    // when
    UserPoint userPoint = pointService.getUserPoint(userId);

    //then
    assertEquals(userPoint.id(), userId);
  }

  /** 테스트케이스 : 사용자 파라메터가 없는 경우 테스트 */
  @Test
  void 사용자_아이디가_null일때 () throws InterruptedException {

    //given
    long userId = 0;

    //then
    assertThrows(IllegalArgumentException.class, () -> pointService.getUserPoint(userId));
  }


  /** 테스트케이스 : 사용자 거래내역 모두 가져오기 */
  @Test
  void 사용자_포인트_거래내역_조회() throws InterruptedException {

    //given
    long userId = 1L;
    pointHistoryTable.insert(userId, 4000L, TransactionType.CHARGE, 9999L);
    pointHistoryTable.insert(userId, 9000L, TransactionType.CHARGE, 9999L);
    pointHistoryTable.insert(userId, 4500L, TransactionType.USE, 9999L);

    //when
    List<PointHistory> pointHistory = pointService.getPointHistory(userId);

    //then
    assertEquals(pointHistory.get(0).userId(), userId);
    assertEquals(pointHistory.get(0).type(), TransactionType.CHARGE);
  }

  @Test
  void 사용자_포인트_거래내역_없는_경우() {
    // given
    long userId = 1L;
    List<PointHistory> emptyList = new ArrayList<>();

    // when
    List<PointHistory> pointHistory = pointService.getPointHistory(userId);

    // then
    assertEquals(pointHistory, emptyList);
  }


  /** 사용자가 충전하는 테스트 */
  @Test
  void 사용자가_포인트_충전() throws InterruptedException {
    //given
    long userId = 1L;
    UserPoint userPoint = pointService.getUserPoint(userId);

    //when
    UserPoint userPoint1 = pointService.charge(userId, 10000L);
    UserPoint userPoint2 = pointService.charge(userId, 20000L);

    //then
    assertEquals(userPoint1.point(), 10000L);
    assertEquals(userPoint2.point(), 30000L);
  }

  /** 0원 충전하는 테스트 */
  @Test
  void 사용자가_0원_포인트_충전() throws InterruptedException {
    //given
    long userId = 1L;
    UserPoint userPoint = pointService.getUserPoint(userId);

    //when


    //then
    assertThrows(IllegalArgumentException.class, () -> pointService.charge(userId, 0L));
    assertThrows(IllegalArgumentException.class, () -> pointService.charge(userId, -1000L));
  }

  /** 사용자가 포인트 사용하는 테스트 */
  @Test
  void 사용자가_포인트_사용하는경우() throws InterruptedException {
    //given
    long userId = 1L;
    UserPoint userPoint = pointService.getUserPoint(userId);
    pointService.charge(userId, 10000L);

    //when
    UserPoint userPoint1 = pointService.use(userId, 5500L);
    UserPoint userPoint2 = pointService.use(userId, 3500L);

    //then
    assertEquals(userPoint1.point(), 4500L);
    assertEquals(userPoint2.point(), 1000L);
  }

  /** 사용자가 포인트 사용하는 테스트 */
  @Test
  void 사용자가_포인트_0원_사용하는경우() throws InterruptedException {
    //given
    long userId = 1L;
    UserPoint userPoint = pointService.getUserPoint(userId);
    pointService.charge(userId, 10000L);

    //when

    //then
    assertThrows(IllegalArgumentException.class, () -> pointService.use(userId, 0L));
    assertThrows(IllegalArgumentException.class, () -> pointService.use(userId, -1000L));
  }

}
