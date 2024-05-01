package com.tdd.concert.domain.user.infra;

import com.tdd.concert.domain.user.model.User;
import com.tdd.concert.domain.user.repository.UserCoreRepository;
import com.tdd.concert.domain.user.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserCoreRepositoryImpl implements UserCoreRepository {

  private final UserJpaRepository userJpaRepository;

  @Override
  public User createUser() {
    User user = new User();
    return userJpaRepository.save(user);
  }

  @Override
  public User findUserById(Long userId) {
    return userJpaRepository.findUserByUserId(userId);
  }

  @Override
  public User findUserByUserIdWithExclusiveLock(Long userId) {
    return userJpaRepository.findUserByUserIdWithExclusiveLock(userId);
  }
}
