package com.tdd.concert.api.usecase;

import com.tdd.concert.api.controller.dto.request.TokenRequest;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
import com.tdd.concert.domain.token.component.TokenManager;
import com.tdd.concert.domain.token.status.ProgressStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.datetime.DateFormatter;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class CreateTokenIntegrationTest {

  @Autowired
  private CreateTokenUseCase createTokenUseCase;

  @Autowired
  private TokenManager tokenManager;

  private String issuedToken = "";

  private List<String> tokenList = new ArrayList<>();


  @DisplayName("토큰이 없어서 토큰을 발급한다.")
  @Test
  void case1() {
    // given : 빈 토큰으로 TokenRequest 객체 생성한다.
    String token = null;
    TokenRequest request = new TokenRequest(token);

    // when : 토큰이 없는 경우 신규토큰을 발행
    TokenResponse response = createTokenUseCase.insertQueue(request);

    // then
    assertNotNull(response);
    assertEquals(ProgressStatus.ONGOING, response.getStatus());
    assertEquals(1L, response.getWaitNo());
    //assertEquals(1L, response.getUserId());

    // DateTimeFormatter를 사용하여 원하는 형식의 문자열로 변환
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    String expectedTime = LocalDateTime.now().plusMinutes(10).format(formatter);
    String actualTime = response.getExpiredAt().format(formatter);

    // 토큰 만료시각을 검증
    assertEquals(expectedTime, actualTime);
  }

  @DisplayName("현재 존재하는 사용자의 토큰인 경우 토큰으로 사용자조회")
  @Test
  void case2() {
    // given : 토큰을 일부러 1개 신규 생성
    String token = null;
    TokenRequest request = new TokenRequest(token);
    TokenResponse response1 = createTokenUseCase.insertQueue(request);

    String token2 = response1.getToken();
    TokenRequest request2 = new TokenRequest(token2);

    // when : 생성된 토큰을 가지고 TokenRequest를 생성하여 대기열 추가 메서드 호출
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
      TokenResponse dummyTokenResponse = createTokenUseCase.insertQueue(request);
      tokenList.add(dummyTokenResponse.getToken()); // @AfterEach에서 토큰을 만료시키기 위해서 리스트로 저장.
    }

    // when
    TokenResponse response = createTokenUseCase.insertQueue(request);

    // then
    assertNotNull(response);
    assertEquals(ProgressStatus.WAIT, response.getStatus());
    assertNull(response.getExpiredAt());
    assertNotNull(response.getToken());

    // 50개의 토큰을 expire 처리
    for(int i = 0; i<50; i++) {
      tokenManager.expireToken(tokenList.get(i));
    }
  }

}
