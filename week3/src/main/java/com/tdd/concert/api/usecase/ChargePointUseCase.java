package com.tdd.concert.api.usecase;

import com.tdd.concert.api.controller.dto.request.PointRequest;
import com.tdd.concert.api.controller.dto.response.PointResponse;
import com.tdd.concert.domain.point.component.PointManager;
import com.tdd.concert.domain.point.model.PointHistory;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChargePointUseCase {

  private final PointManager pointManager;
  private final UserManager userManager;

  public PointResponse charge(PointRequest request) {

    User user = userManager.chargePoint(request.getUserId(), request.getAmount());
    request.setUser(user);
    PointHistory pointHistory = pointManager.insertPointHistory(request);

    PointResponse response = new PointResponse(user.getUserId(), user.getPoint());
    return response;
  }

}
