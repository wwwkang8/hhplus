package com.tdd.concert.domain.user.repository;

import com.tdd.concert.domain.user.model.User;

public interface UserCoreRepository {

  public User createUser();

  public User findUserById(Long userId);

  public User findUserByUserIdWithExclusiveLock(Long userId);

}
