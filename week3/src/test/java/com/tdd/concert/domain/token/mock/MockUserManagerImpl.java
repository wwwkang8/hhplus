package com.tdd.concert.domain.token.mock;

import java.util.ArrayList;
import java.util.List;

import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;

public class MockUserManagerImpl implements UserManager {

  List<User> userList = new ArrayList<>();

  @Override
  public User createUser() {

    long userId = 0L;
    User user = new User(++userId, 0);
    userList.add(user);

    return user;
  }

  @Override
  public User findUserById(Long userId) {
    return null;
  }

  @Override
  public User chargePoint(long userId, int amount) {
    return null;
  }

  @Override
  public User usePoint(long userId, int amount) {
    return null;
  }
}
