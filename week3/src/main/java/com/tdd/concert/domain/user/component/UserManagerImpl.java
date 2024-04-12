package com.tdd.concert.domain.user.component;

import com.tdd.concert.domain.user.model.User;
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
}
