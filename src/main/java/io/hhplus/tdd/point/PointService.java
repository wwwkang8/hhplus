package io.hhplus.tdd.point;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PointService {

  @Autowired
  private UserPointTable userPointTable;

  @Autowired
  private PointHistoryTable pointHistoryTable;

  public UserPoint getUserPoint(long userId) throws InterruptedException {

    if(userId == 0) {
      throw new IllegalArgumentException("사용자 아이디 입력 오류입니다. 올바른 사용자 아이디를 입력해주세요");
    }

    return userPointTable.selectById(userId);

  }

  public List<PointHistory> getPointHistory(long userId) {

    if(userId == 0) {
      throw new IllegalArgumentException("사용자 아이디 입력 오류입니다. 올바른 사용자 아이디를 입력해주세요");
    }

    return pointHistoryTable.selectAllByUserId(userId);
  }

  /** 포인트 충전 */
  public UserPoint charge(Long userId, Long amount) throws InterruptedException {

    /** 0원 검증 */
    if(amount <= 0) {
      throw new IllegalArgumentException("충전금액이 0원이하일 수 없습니다.");
    }

    UserPoint userPoint = getUserPoint(userId);

    /** 잔액증가 */
    long totalBalance = userPoint.point() + amount;
    UserPoint userAfterTrns = userPointTable.insertOrUpdate(userId, totalBalance);

    /** 거래내역 추가 */
    pointHistoryTable.insert(userId, amount, TransactionType.CHARGE, 10L);

    return userAfterTrns;
  }

  /** 포인트 사용 */
  public UserPoint use(Long userId, Long amount) throws InterruptedException {

    /** 0원 검증 */
    if(amount <= 0) {
      throw new IllegalArgumentException("사용금액이 0원이하일 수 없습니다.");
    }

    UserPoint userPoint = getUserPoint(userId);

    /** 잔액 검증 */
    if(userPoint.point() < amount) {
      throw new IllegalArgumentException("잔액이 부족합니다.");
    }

    /** 잔액감 */
    long totalBalance = userPoint.point() - amount;
    UserPoint userAfterTrns = userPointTable.insertOrUpdate(userId, totalBalance);

    /** 거래내역 추가 */
    pointHistoryTable.insert(userId, amount, TransactionType.USE, 10L);

    return userAfterTrns;

  }









}
