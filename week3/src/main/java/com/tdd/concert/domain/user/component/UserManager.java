package com.tdd.concert.domain.user.component;

import com.tdd.concert.domain.user.model.User;

public interface UserManager {

  public User createUser();

  public User findUserById(Long userId);

  User chargePoint(long userId, int amount);

  User usePoint(long userId, int amount);
}
