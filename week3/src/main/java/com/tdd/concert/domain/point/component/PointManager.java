package com.tdd.concert.domain.point.component;

import com.tdd.concert.api.controller.dto.request.PointRequest;
import com.tdd.concert.domain.point.model.PointHistory;
import com.tdd.concert.domain.point.model.PointRscd;
import com.tdd.concert.domain.point.repository.PointCoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PointManager {

  private final PointCoreRepository pointCoreRepository;

  public PointHistory insertPointHistory(PointRequest request) {
    PointHistory pointHistory = PointHistory.makePointHistory(request);
    return pointCoreRepository.save(pointHistory);
  }

  public int countPointHistoriesByUserId(PointRscd pointRscd) {
    return pointCoreRepository.countPointHistoriesByUserId(pointRscd);
  }
}
