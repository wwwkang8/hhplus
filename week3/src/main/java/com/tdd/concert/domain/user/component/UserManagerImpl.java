package com.tdd.concert.domain.user.component;

import com.tdd.concert.domain.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserManagerImpl implements UserManager{

  private final UserStore userStore;
  private final UserReader userReader;


  @Override
  public User createUser() {
    return userStore.createUser();
  }

  @Override
  public User findUserById(Long userId) {
    return userReader.findUserById(userId);
  }

  @Override
  @Transactional
  public User chargePoint(long userId, int amount) {

    if(amount <= 0) {
      throw new RuntimeException("0원 이하는 충전할 수 없습니다.");
    }

    //User user = userReader.findUserById(userId);
    /** 사용자 포인트 충전의 요청을 순차적으로 받기 위해 비관적 락을 사용 */
    User user = userReader.findUserByUserIdWithExclusiveLock(userId);
    int totalPoint = user.getPoint() + amount;
    user.setPoint(totalPoint);
    return user;
  }

  @Override
  @Transactional
  public User usePoint(long userId, int amount) {
    User user = userReader.findUserById(userId);
    int totalPoint = user.getPoint() - amount;

    if(amount <= 0) {
      throw new RuntimeException("0원 이하는 사용할 수 없습니다.");
    }

    if(totalPoint < 0) {
      throw new RuntimeException("잔액이 부족합니다. 사용자ID : "+userId+", 잔액:"+user.getPoint());
    }

    user.setPoint(totalPoint);
    return user;
  }
}
