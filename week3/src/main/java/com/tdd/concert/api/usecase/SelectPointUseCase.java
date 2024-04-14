package com.tdd.concert.api.usecase;

import com.tdd.concert.api.controller.dto.response.UserResponse;
import com.tdd.concert.domain.user.component.UserManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SelectPointUseCase {

  private final UserManager userManager;


}
