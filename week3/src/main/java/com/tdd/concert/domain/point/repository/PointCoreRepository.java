package com.tdd.concert.domain.point.repository;

import com.tdd.concert.domain.point.model.PointHistory;

public interface PointCoreRepository {

  public PointHistory save(PointHistory pointHistory);

}
