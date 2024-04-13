package com.tdd.concert.token.mock;

import java.util.ArrayList;
import java.util.List;

import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import com.tdd.concert.domain.user.repository.UserCoreRepository;
import com.tdd.concert.domain.user.repository.UserRepository;

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
}
