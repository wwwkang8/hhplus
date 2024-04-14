package com.tdd.concert.domain.point.infra;

import com.tdd.concert.domain.point.model.PointHistory;
import com.tdd.concert.domain.point.repository.PointCoreRepository;
import com.tdd.concert.domain.point.repository.PointJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PointCoreRepositoryImpl implements PointCoreRepository {

  private final PointJpaRepository pointJpaRepository;

  @Override
  public PointHistory save(PointHistory pointHistory) {
    return pointJpaRepository.save(pointHistory);
  }
}
