package com.tdd.concert.domain.user.component;

import com.tdd.concert.domain.user.model.User;
import com.tdd.concert.domain.user.repository.UserCoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserReader {

  private final UserCoreRepository userCoreRepository;

  public User findUserById(Long userId) {

    return userCoreRepository.findUserById(userId);
  }
}
