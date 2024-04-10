package com.tdd.concert.token.component;

import java.time.LocalDateTime;
import java.util.UUID;

import com.tdd.concert.domain.token.component.TokenGenerator;
import com.tdd.concert.domain.token.component.TokenReader;
import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.status.ProgressStatus;
import com.tdd.concert.domain.user.model.User;
import com.tdd.concert.token.mock.MockTokenCoreRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TokenGeneratorTest {

  private TokenGenerator tokenGenerator;
  private TokenReader tokenReader;
  private MockTokenCoreRepositoryImpl mockTokenCoreRepository;

  @BeforeEach
  void setUp() {
     mockTokenCoreRepository = new MockTokenCoreRepositoryImpl();
     tokenGenerator = new TokenGenerator(mockTokenCoreRepository);
    tokenReader = new TokenReader(mockTokenCoreRepository);
  }

  @DisplayName("토큰 테이블에 INSERT 하는 테스트")
  @Test
  void case1() {
    //given
    long userId = 1L;
    User user = new User();
    user.setUserId(userId);
    Token token = new Token(1L, UUID.randomUUID().toString(), ProgressStatus.ONGOING, 1L, LocalDateTime.now(), LocalDateTime.now() ,LocalDateTime.now().plusMinutes(10), user);

    // when
    Token savedToken = tokenGenerator.insertTokenTable(token);

    // then
    assertEquals(token.getId() , savedToken.getId());
    assertEquals(token.getToken() , savedToken.getToken());
  }

  @DisplayName("사용자ID의 토큰을 발급한다")
  @Test
  void case2() {

    // given : userId로 토큰 생성
    long userId = 1L;
    String combinate = userId + "";
    String expectedToken = UUID.nameUUIDFromBytes(combinate.getBytes()).toString();

    // when : 실제로 TokenGenerator에서 생성한 토큰
    String actualToken = tokenGenerator.generateToken(userId);

    // then
    assertEquals(expectedToken, actualToken);
  }

  @DisplayName("서로 다른 사용자ID로 토큰을 발급한다.")
  @Test
  void case3() {

    // given : userId로 토큰 생성
    long userId = 1L;
    String combinate = userId + "";
    String expectedToken = UUID.nameUUIDFromBytes(combinate.getBytes()).toString();

    // when : 사용자 아이디를 다르게 하여 토큰 발급
    long userId2 = 2L;
    String actualToken = tokenGenerator.generateToken(userId2);

    // then : 다른 사용자 ID로 토큰 발급했으니, 서로 다른 UUID가 발급되어야 함.
    assertNotEquals(expectedToken, actualToken);
  }

  @DisplayName("대기순번 채번하는 테스트")
  @Test
  void case4() {

    // given : 토큰 테이블에 대기인원 10명을 INSERT
    long userId = 0L;
    long tokenId = 0L;
    long waitNo = 0L;

    for(int i=0; i<10; i++) {
      userId++; tokenId++; waitNo++;
      User user = new User();
      user.setUserId(userId);

      Token token = new Token(tokenId, UUID.randomUUID().toString(), ProgressStatus.WAIT, waitNo, LocalDateTime.now(), LocalDateTime.now() ,LocalDateTime.now().plusMinutes(10), user);
      mockTokenCoreRepository.save(token);
      System.out.println("waitNo : " + waitNo);
    }
    long expectedWaitNo = mockTokenCoreRepository.selectNextWaitNo();
    System.out.println(" expectedWaitNo : " + expectedWaitNo);


    // when : 다음 대기순번 채번
    long actualNextWaitNo = tokenReader.selectNextWaitNo();
    System.out.println(" actualNextWaitNo : " + actualNextWaitNo);

    // then : 이건 테스트 짜는게 어렵다.
    assertEquals(expectedWaitNo, actualNextWaitNo);

  }

  @DisplayName("현재 예약진행중인 고객 수 확인")
  @Test
  void case5() {
    // given
    long userId = 0L;
    long tokenId = 0L;
    long waitNo = 0L;

    for(int i=0; i<20; i++) {
      userId++; tokenId++; waitNo++;
      User user = new User();
      user.setUserId(userId);

      Token token = new Token(tokenId, UUID.randomUUID().toString(), ProgressStatus.ONGOING, waitNo, LocalDateTime.now(), LocalDateTime.now() ,LocalDateTime.now().plusMinutes(10), user);
      mockTokenCoreRepository.save(token);
    }
    long expectedOngoingCount = waitNo;


    // when
    long actualOngoingCount = tokenReader.getProgressStatusCount(ProgressStatus.ONGOING);


    // then
    assertEquals(expectedOngoingCount, actualOngoingCount);
  }

  @DisplayName("예약진행중인 고객이 50명미만일 때는 추가사용자의 상태값을 예약가능상태로 설정")
  @Test
  void case6() {
    // given
    long userId = 0L;
    long tokenId = 0L;
    long waitNo = 0L;

    /** 45명의 사용자가 이미 ONGOING 상태로 설정. 그러면 46번째 사용자는 ONGOING 상태로 대기열에 추가된다 */
    for(int i=0; i<45; i++) {
      userId++; tokenId++; waitNo++;
      User user = new User();
      user.setUserId(userId);

      Token token = new Token(tokenId, UUID.randomUUID().toString(), ProgressStatus.ONGOING, waitNo, LocalDateTime.now(), LocalDateTime.now() ,LocalDateTime.now().plusMinutes(10), user);
      mockTokenCoreRepository.save(token);
    }

    // when : 신규로 추가되는 사용자의 초기 예약상태는 ONGOING으로 설정
    ProgressStatus actualStatus = tokenReader.getCurrentQueueStatus();

    // then
    assertEquals(ProgressStatus.ONGOING, actualStatus);
  }


  @DisplayName("예약진행중인 고객이 50명초과일 때 추가 사용자의 예약상태값을 대기로 설정")
  @Test
  void case7() {
    // given
    long userId = 0L;
    long tokenId = 0L;
    long waitNo = 0L;

    /** 50명의 사용자가 이미 ONGOING 상태로 설정. 그러면 51번째 사용자는 WAIT 상태로 대기열에 추가된다 */
    for(int i=0; i<50; i++) {
      userId++; tokenId++; waitNo++;
      User user = new User();
      user.setUserId(userId);

      Token token = new Token(tokenId, UUID.randomUUID().toString(), ProgressStatus.ONGOING, waitNo, LocalDateTime.now(), LocalDateTime.now() ,LocalDateTime.now().plusMinutes(10), user);
      mockTokenCoreRepository.save(token);
    }

    // when : 신규로 추가되는 사용자의 예약상태는 WAIT으로 설정(왜? 현재 ONGOING이 50명이 넘어서)
    ProgressStatus actualStatus = tokenReader.getCurrentQueueStatus();

    // then
    assertEquals(ProgressStatus.WAIT, actualStatus);
  }

}
