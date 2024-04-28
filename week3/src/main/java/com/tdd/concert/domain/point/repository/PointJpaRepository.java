package com.tdd.concert.domain.point.repository;

import com.tdd.concert.domain.point.model.PointHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointJpaRepository extends JpaRepository<PointHistory, Long> {
}
