package com.tdd.concert.token.component;

import java.time.LocalDateTime;
import java.util.UUID;

import com.tdd.concert.domain.token.component.TokenGenerator;
import com.tdd.concert.domain.token.component.TokenValidator;
import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.status.ProgressStatus;
import com.tdd.concert.domain.user.model.User;
import com.tdd.concert.token.mock.MockTokenCoreRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;

public class TokenValidatorTest {

  private TokenValidator tokenValidator;
  private MockTokenCoreRepositoryImpl mockTokenCoreRepository;

  @BeforeEach
  void setUp() {
    mockTokenCoreRepository = new MockTokenCoreRepositoryImpl();
    tokenValidator = new TokenValidator(mockTokenCoreRepository);

    /** 임의의 토큰 50개를 대기열에 추가 */
    long userId = 0L;
    long tokenId = 0L;
    long waitNo = 0L;

    for(int i=0; i<50; i++) {
      userId++; tokenId++; waitNo++;
      User user = new User();
      user.setUserId(userId);

      Token token = new Token(tokenId, UUID.randomUUID().toString(), ProgressStatus.ONGOING, waitNo, LocalDateTime
          .now(), LocalDateTime.now() ,LocalDateTime.now().plusMinutes(10), user);
      mockTokenCoreRepository.save(token);
    }
  }

  @DisplayName("대기열에 존재하지 않는 토큰 조회")
  @Test
  void case1() {
    // given : 존재하지 않는 랜덤 토큰 설정
    String invalidToken = "aaaa";


    // then
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      tokenValidator.validateToken(invalidToken);
    });
    assertEquals("대기열에 존재하지 않는 토큰입니다. 토큰 : " + invalidToken, exception.getMessage());
  }

  @DisplayName("만료시각이 지난 토큰")
  @Test
  void case2() {
    // given : 현재시간에서 10분전이 만료시각인 토큰을 생성
    Token expiredToken = mockTokenCoreRepository.findByWaitNo(1L);
    expiredToken.setExpiredAt(LocalDateTime.now().minusMinutes(10));


    // then
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      tokenValidator.validateToken(expiredToken.getToken());
    });
    assertEquals("만료시각이 지난 토큰입니다. 토큰 : " + expiredToken.getToken() + ", 토큰만료시각 : " + expiredToken.getExpiredAt()
                  , exception.getMessage());
  }


  @DisplayName("만료시각이 지난 토큰")
  @Test
  void case3() {
    // given : 현재시간에서 10분전이 만료시각인 토큰을 생성
    Token invalidToken = mockTokenCoreRepository.findByWaitNo(1L);
    invalidToken.setProgressStatus(ProgressStatus.FINISHED);


    // then
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      tokenValidator.validateToken(invalidToken.getToken());
    });
    assertEquals("예약이 완료된 토큰입니다. 토큰 : " + invalidToken.getToken() + ", 토큰 상태 : " + invalidToken.getProgressStatus()
        , exception.getMessage());
  }

}
