package io.hhplus.tdd;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.PointService;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
  @DisplayName("사용자의 포인트 조회")
  void 사용자_포인트_조회() throws InterruptedException {
    // given : UserPointTable에 5000포인트를 가진 사용자 등록
    long userId = 1L;
    userPointTable.insertOrUpdate(userId, 5000L);

    // when : PointService로 사용자 조회
    UserPoint userPoint = pointService.getUserPoint(userId);

    // then : 예상된 사용자 아이디와 실제 조회된 사용자 아이디를 비교
    assertEquals(userId, userPoint.id());
    assertEquals(5000L, userPoint.point());
  }

  @Test
  @DisplayName("사용자가 없는 경우")
  void 사용자가_없는_경우() throws InterruptedException {
    //given : 존재하지 않는 사용자 아이디로 조회하는 경우
    long userId = 2L;

    // when : 사용자를 조회한다.
    UserPoint userPoint = pointService.getUserPoint(userId);

    //then : 사용자가 없는 경우 새로운 사용자를 생성해서 반환한다.
    assertEquals(userPoint.id(), userId);
  }


  @Test
  @DisplayName("사용자_아이디가_0L일때")
  void 사용자_아이디가_0L일때 () throws InterruptedException {
    //given : 사용자 아이디가 0L로 세팅.
    long userId = 0L;

    //then : 사용자 아이디가 0L인 경우에 오류 발생
    assertThrows(IllegalArgumentException.class, () -> pointService.getUserPoint(userId));
  }


  @Test
  @DisplayName("사용자 포인트 거래내역 조회")
  void 사용자_포인트_거래내역_조회() throws InterruptedException {

    /**
     * 테스트 수정할 것 : pointHistoryTable이 다른 테스트의 거래내역에
     * 영향을 받아서 이 테스트가 깨지는 경우가 있다. 거래내역 초기화가 필요.
     * */

    //given : 사용자의 포인트 충전, 사용 거래내역을 생성한다.
    long userId = 1L;
    pointHistoryTable.insert(userId, 4000L, TransactionType.CHARGE, 9999L);
    pointHistoryTable.insert(userId, 9000L, TransactionType.CHARGE, 9999L);
    pointHistoryTable.insert(userId, 4500L, TransactionType.USE, 9999L);

    //when : 사용자아이디로 포인트 거래내역을 조회한다.
    List<PointHistory> pointHistory = pointService.getPointHistory(userId);

    //then : 사용자의 거래내역이 예상한대로 맞는지 비교한다.
    assertEquals(userId, pointHistory.get(0).userId());
    assertEquals(TransactionType.CHARGE, pointHistory.get(0).type());
    assertEquals(userId, pointHistory.get(1).userId());
    assertEquals(TransactionType.CHARGE, pointHistory.get(1).type());
    assertEquals(userId, pointHistory.get(2).userId());
    assertEquals(TransactionType.USE, pointHistory.get(2).type());
  }

  @Test
  @DisplayName("사용자의_포인트_거래내역_없는_경우")
  void 사용자의_포인트_거래내역_없는_경우() {
    // given
    long userId = 999L;
    List<PointHistory> emptyList = new ArrayList<>();

    // when
    List<PointHistory> pointHistory = pointService.getPointHistory(userId);

    // then
    assertEquals(pointHistory, emptyList);
  }


  @Test
  @DisplayName("사용자가_포인트_충전")
  void 사용자가_포인트_충전() throws InterruptedException {
    //given : 사용자 아이디에 해당하는 포인트를 초기화 한다.
    long userId = 1L;
    userPointTable.insertOrUpdate(userId, 0L); // 사용자 포인트 초기화

    //when : 사용자의 포인트를 충전.
    UserPoint userPoint1 = pointService.charge(userId, 10000L);
    UserPoint userPoint2 = pointService.charge(userId, 20000L);

    //then : 충전한 금액에 따라서 검증
    assertEquals(10000L, userPoint1.point());
    assertEquals(30000L, userPoint2.point());
  }


  @Test
  @DisplayName("사용자가 0원이하 포인트 충전")
  void 사용자가_0원이하_포인트_충전() throws InterruptedException {
    //given : 사용자 조회
    long userId = 1L;
    UserPoint userPoint = pointService.getUserPoint(userId);

    //when


    //then회 : 사용자가 0원이하로 포인트 충전시 오류 발생.
    assertThrows(IllegalArgumentException.class, () -> pointService.charge(userId, 0L));
    assertThrows(IllegalArgumentException.class, () -> pointService.charge(userId, -1000L));
  }


  @Test
  @DisplayName("사용자가 포인트를 사용함")
  void 사용자가_포인트_사용하는경우() throws InterruptedException {
    //given : 사용자의 포인트를 초기화 후 충전.
    long userId = 1L;
    userPointTable.insertOrUpdate(userId, 0L); // 사용자의 포인트 초기화
    pointService.charge(userId, 10000L);

    //when : 사용자가 5500원, 3500원 순서로 포인트 사용.
    UserPoint userPoint1 = pointService.use(userId, 5500L);
    UserPoint userPoint2 = pointService.use(userId, 3500L);

    //then : 사용자의 포인트 잔액을 검증
    assertEquals(4500L, userPoint1.point());
    assertEquals(1000L, userPoint2.point());
  }


  @Test
  @DisplayName("잔액보다 포인트를 많이 사용함")
  void 잔액보다_포인트를_많이_사용함() throws InterruptedException {
    //given : 사용자의 포인트 잔액을 5000원으로 세
    long userId = 1L;
    userPointTable.insertOrUpdate(userId, 5000L);

    //when

    //then팅 : 사용자의 포인트 잔액(5000원)보다 많은 금액을 사용했을 때 오류 메시지 확인
    assertThrows(IllegalArgumentException.class, () -> pointService.use(userId, 20000L));
  }


  @Test
  @DisplayName("사용자가 포인트 0원이하 사용함.")
  void 사용자가_포인트_0원이하_사용하는경우() throws InterruptedException {
    //given : 사용자의 포인트
    long userId = 1L;
    UserPoint userPoint = pointService.getUserPoint(userId);

    //when

    //then
    assertThrows(IllegalArgumentException.class, () -> pointService.use(userId, 0L));
    assertThrows(IllegalArgumentException.class, () -> pointService.use(userId, -1000L));
  }

}
