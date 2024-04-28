package com.tdd.concert.domain.concert.repository;

import com.tdd.concert.domain.concert.model.ConcertSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertScheduleJpaRepository extends JpaRepository<ConcertSchedule, Long> {
}
