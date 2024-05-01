package com.tdd.concert.domain.point.repository;

import com.tdd.concert.domain.point.model.PointHistory;
import com.tdd.concert.domain.point.model.PointRscd;

public interface PointCoreRepository {

  public PointHistory save(PointHistory pointHistory);

  public int countPointHistoriesByUserId(PointRscd pointRscd);

}
