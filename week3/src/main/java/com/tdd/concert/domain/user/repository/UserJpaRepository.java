package com.tdd.concert.domain.user.repository;

import com.tdd.concert.domain.user.model.User;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<User, Long> {

  public User findUserByUserId(Long userId);


  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select u from User u where u.userId = ?1")
  public User findUserByUserIdWithExclusiveLock(Long userId);


}
