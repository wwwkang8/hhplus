package com.tdd.concert.domain.point.repository;

import com.tdd.concert.domain.point.model.PointHistory;
import com.tdd.concert.domain.point.model.PointRscd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointJpaRepository extends JpaRepository<PointHistory, Long> {


  @Query("SELECT count(ph) FROM PointHistory ph WHERE ph.pointRscd = ?1")
  public int countPointHistoriesByUserId(PointRscd pointRscd);

}
