package io.hhplus.tdd.database;

import io.hhplus.tdd.point.UserPoint;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 해당 Table 클래스는 변경하지 않고 공개된 API 만을 사용해 데이터를 제어합니다.
 */
@Component
public class UserPointTable {
  private Map<Long, UserPoint> table = new HashMap<>();

  public UserPoint selectById(Long id) throws InterruptedException {
    throttle(200);
    UserPoint userPoint = table.get(id);

    if (userPoint == null) {
      return new UserPoint(id, 0L, System.currentTimeMillis());
    }
    return userPoint;
  }

  public UserPoint insertOrUpdate(Long id, Long amount) throws InterruptedException {
    //Thread.sleep(Long.parseLong(String.valueOf(Math.random())) * 300L);

    UserPoint userPoint = new UserPoint(id, amount, System.currentTimeMillis());
    table.put(id, userPoint);

    return userPoint;
  }

  private void throttle(long millis) {
    try {
      TimeUnit.MILLISECONDS.sleep((long) (Math.random() * millis));
    } catch (InterruptedException ignored) {

    }
  }
}
