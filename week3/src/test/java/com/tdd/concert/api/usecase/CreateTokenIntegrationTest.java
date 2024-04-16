package com.tdd.concert.api.usecase;

import com.tdd.concert.api.controller.dto.request.TokenRequest;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
import com.tdd.concert.domain.token.component.TokenManager;
import com.tdd.concert.domain.token.status.ProgressStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.datetime.DateFormatter;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class CreateTokenIntegrationTest {

  @Autowired
  private CreateTokenUseCase createTokenUseCase;

  @Autowired
  private TokenManager tokenManager;

  private String issuedToken = "";

  @BeforeEach
  void setUp() {


  }

  @DisplayName("토큰이 없어서 토큰을 발급한다.")
  @Test
  void case1() {
    // given
    String token = null;
    TokenRequest request = new TokenRequest(token);

    // when
    TokenResponse response = createTokenUseCase.insertQueue(request);

    // then
    assertNotNull(response);
    assertEquals(ProgressStatus.ONGOING, response.getStatus());
    assertEquals(1L, response.getWaitNo());
    assertEquals(1L, response.getUserId());

    // DateTimeFormatter를 사용하여 원하는 형식의 문자열로 변환
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    String expectedTime = LocalDateTime.now().plusMinutes(10).format(formatter);
    String actualTime = response.getExpiredAt().format(formatter);

    assertEquals(expectedTime, actualTime);
  }

  @DisplayName("현재 존재하는 사용자의 토큰인 경우 토큰으로 사용자조회")
  @Test
  void case2() {
    // given
    String token = null;
    TokenRequest request = new TokenRequest(token);
    TokenResponse response1 = createTokenUseCase.insertQueue(request);

    String token2 = response1.getToken();
    TokenRequest request2 = new TokenRequest(token2);

    // when
    TokenResponse response = createTokenUseCase.insertQueue(request2);

    // then
    assertNotNull(response);
    assertEquals(ProgressStatus.ONGOING, response.getStatus());

    // DateTimeFormatter를 사용하여 원하는 형식의 문자열로 변환
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    String expectedTime = LocalDateTime.now().plusMinutes(10).format(formatter);
    String actualTime = response.getExpiredAt().format(formatter);

    assertEquals(expectedTime, actualTime);
  }

  @DisplayName("50명이 넘는 사용자가 대기열에 들어오는 경우 51번쨰 사용자는 대기상태")
  @Test
  void case3() {
    // given
    String token = null;
    TokenRequest request = new TokenRequest(token);

    // 50명의 대기자를 대기열에 추가
    for(int i = 0; i<50; i++) {
      createTokenUseCase.insertQueue(request);
    }

    // when
    TokenResponse response = createTokenUseCase.insertQueue(request);

    // then
    assertNotNull(response);
    assertEquals(ProgressStatus.WAIT, response.getStatus());
    assertEquals(null, response.getExpiredAt());
    assertNotNull(response.getUserId());
    assertNotNull(response.getToken());
  }

}
