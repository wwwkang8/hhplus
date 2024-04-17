package com.tdd.concert.api.usecase;

import com.tdd.concert.api.controller.dto.request.TokenRequest;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
import com.tdd.concert.domain.token.component.TokenManager;
import com.tdd.concert.domain.token.infra.TokenCoreRepositoryImpl;
import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.status.ProgressStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

@SpringBootTest
public class ValidateTokenIntegrationTest {

  @Autowired
  private ValidateTokenUseCase validateTokenUseCase;

  @Autowired
  private TokenManager tokenManager;

  @Autowired
  private TokenCoreRepositoryImpl tokenCoreRepository;

  private TokenResponse tokenResponse;

  @BeforeEach
  void setUp() {
    TokenRequest request = new TokenRequest();
    tokenResponse = tokenManager.insertQueue(request);
  }

  @DisplayName("유효한 토큰인 경우")
  @Test
  void case1() {
    // given
    String token = tokenResponse.getToken();
    TokenRequest request = new TokenRequest(token);

    // when
    TokenResponse actualResponse = validateTokenUseCase.validateToken(request);

    // then
    assertEquals(token, actualResponse.getToken());
    assertEquals(1L, actualResponse.getUserId());
    assertEquals(ProgressStatus.ONGOING, actualResponse.getStatus());
  }

  @DisplayName("존재하지 않는 토큰인 경우 오류 발생.")
  @Test
  void case2() {
    // given
    String token = "AAAABBBB";
    TokenRequest request = new TokenRequest(token);


    // when, then
    RuntimeException exception = assertThrows(RuntimeException.class, ()->validateTokenUseCase.validateToken(request));
    String expectedMessage = "대기열에 존재하지 않는 토큰입니다. 토큰 :" + token;
    assertEquals(expectedMessage, exception.getMessage());
  }

  @DisplayName("만료시각이 지난 토큰의 경우 오류 발생.")
  @Test
  void case3() {
    // given : 만료시각을 지나도록 강제 세팅
    String token = tokenResponse.getToken();
    Token tokenEntity = tokenCoreRepository.findByToken(token);
    tokenEntity.setExpiredAt(LocalDateTime.now().minusMinutes(12));
    tokenCoreRepository.save(tokenEntity);

    TokenRequest request = new TokenRequest(token);


    // when, then
    RuntimeException exception = assertThrows(RuntimeException.class, ()->validateTokenUseCase.validateToken(request));
    String expectedMessage = "만료시각이 지난 토큰입니다. 토큰 : " + tokenEntity.getToken() + ", 토큰만료시각 : " + tokenEntity.getExpiredAt();
    assertEquals(expectedMessage, exception.getMessage());
  }

  @DisplayName("예약이 완료되어 만료된 토큰인 경우 오류 발생")
  @Test
  void case4() {
    // given : 토큰의 상태값을 강제로 변경
    String token = tokenResponse.getToken();
    Token tokenEntity = tokenCoreRepository.findByToken(token);
    tokenEntity.setProgressStatus(ProgressStatus.FINISHED);
    tokenCoreRepository.save(tokenEntity);

    TokenRequest request = new TokenRequest(token);


    // when, then
    RuntimeException exception = assertThrows(RuntimeException.class, ()->validateTokenUseCase.validateToken(request));
    String expectedMessage = "예약이 완료된 토큰입니다. 토큰 : " + tokenEntity.getToken() + ", 토큰 상태 : " + tokenEntity.getProgressStatus();
    assertEquals(expectedMessage, exception.getMessage());
  }
}
