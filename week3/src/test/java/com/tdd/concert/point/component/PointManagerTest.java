package com.tdd.concert.point.component;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.tdd.concert.api.controller.dto.request.PointRequest;
import com.tdd.concert.domain.point.component.PointManager;
import com.tdd.concert.domain.point.model.PointHistory;
import com.tdd.concert.domain.point.model.PointRscd;
import com.tdd.concert.domain.point.repository.PointCoreRepository;
import com.tdd.concert.domain.user.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PointManagerTest {

  @Mock
  private PointCoreRepository pointCoreRepository;

  @InjectMocks
  private PointManager pointManager;

  private PointRequest request;

  @BeforeEach
  void setUp() {
    request= new PointRequest(1L, 5000, PointRscd.RECEIVE);
  }

  @DisplayName("포인트 내역을 생성한다.")
  @Test
  void case1() {
    // given
    PointHistory expectedPointHistory = PointHistory.makePointHistory(request);
    when(pointCoreRepository.save(any())).thenReturn(expectedPointHistory);

    // when
    PointHistory actualPointHistory = pointManager.insertPointHistory(request);

    // then
    assertEquals(expectedPointHistory, actualPointHistory);
    assertEquals(expectedPointHistory.getPointHistoryId(), actualPointHistory.getPointHistoryId());
    assertEquals(expectedPointHistory.getPointRscd(), actualPointHistory.getPointRscd());
    assertEquals(expectedPointHistory.getAmount(), actualPointHistory.getAmount());
  }

}
