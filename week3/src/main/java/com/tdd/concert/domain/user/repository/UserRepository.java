package com.tdd.concert.domain.user.repository;

import com.tdd.concert.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
