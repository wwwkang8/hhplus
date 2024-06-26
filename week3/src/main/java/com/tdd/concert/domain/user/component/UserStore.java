package com.tdd.concert.domain.user.component;

import com.tdd.concert.domain.user.model.User;
import com.tdd.concert.domain.user.repository.UserCoreRepository;
import com.tdd.concert.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserStore {

  private final UserCoreRepository userCoreRepository;
  private final UserJpaRepository userJpaRepository;

  public User createUser() {
    return userCoreRepository.createUser();
  }

}
