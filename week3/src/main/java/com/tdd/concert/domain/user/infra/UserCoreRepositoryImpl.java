package com.tdd.concert.domain.user.infra;

import com.tdd.concert.domain.user.model.User;
import com.tdd.concert.domain.user.repository.UserCoreRepository;
import com.tdd.concert.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserCoreRepositoryImpl implements UserCoreRepository {

  private final UserRepository userRepository;

  @Override
  public User createUser() {
    User user = new User();
    return userRepository.save(user);
  }
}
