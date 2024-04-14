package com.tdd.concert.api.usecase;

import com.tdd.concert.api.controller.dto.request.PointRequest;
import com.tdd.concert.api.controller.dto.response.PointResponse;
import com.tdd.concert.api.controller.dto.response.UserResponse;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GetPointUseCase {

  private final UserManager userManager;

  public PointResponse getPoint(long userId) {
    User user = userManager.findUserById(userId);
    return new PointResponse(user.getUserId(), user.getPoint());
  }


}
