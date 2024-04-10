package com.tdd.concert.token.component;

import static org.mockito.Mockito.when;

import com.tdd.concert.domain.token.component.TokenGenerator;
import com.tdd.concert.domain.token.component.TokenManager;
import com.tdd.concert.domain.token.component.TokenManagerImpl;
import com.tdd.concert.domain.token.component.TokenValidator;
import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.status.ProgressStatus;
import com.tdd.concert.domain.user.component.UserManager;
import com.tdd.concert.domain.user.component.UserManagerImpl;
import com.tdd.concert.domain.user.model.User;
import com.tdd.concert.dto.request.TokenRequestDto;
import com.tdd.concert.dto.response.TokenResponseDto;
import com.tdd.concert.token.mock.MockTokenCoreRepositoryImpl;
import com.tdd.concert.token.mock.MockUserManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class TokenManagerTest {

  @InjectMocks
  private TokenManagerImpl tokenManagerImpl;

  @Mock
  private TokenGenerator tokenGenerator;

  @Mock
  private TokenValidator tokenValidator;

  @Mock
  private UserManagerImpl userManagerImpl;

  private MockTokenCoreRepositoryImpl mockTokenCoreRepository;


  @BeforeEach
  void setUp() {
    mockTokenCoreRepository = new MockTokenCoreRepositoryImpl();
  }


  @DisplayName("사용자를 대기열에 추가하기")
  @Test
  void case1() {
    // given
//    TokenRequestDto request = new TokenRequestDto();
//    User user = new User(1L, 0);
//    Token tokenEntity = Token.builder()
//        .user(user)
//        .token("aaaaaa")
//        .waitNo(1L)
//        .progressStatus(ProgressStatus.ONGOING)
//        .createdAt(LocalDateTime.now())
//        .expiredAt(LocalDateTime.now().plusMinutes(10)) //토큰이 10분간 유효함.
//        .build();
//    when(tokenGenerator.insertTokenTable(tokenEntity)).thenReturn(tokenEntity);
//
//    // when
//    TokenResponseDto response = tokenManagerImpl.insertQueue(request);
//
//    // then
//    assertEquals(1L, response.getUserId());
  }































}
