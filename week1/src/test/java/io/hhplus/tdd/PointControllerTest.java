package io.hhplus.tdd;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.print.attribute.standard.Media;
import java.util.List;
import java.util.logging.Logger;

import io.hhplus.tdd.database.PointHistoryTable;
import io.hhplus.tdd.database.UserPointTable;
import io.hhplus.tdd.point.PointController;
import io.hhplus.tdd.point.PointHistory;
import io.hhplus.tdd.point.PointService;
import io.hhplus.tdd.point.TransactionType;
import io.hhplus.tdd.point.UserPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class PointControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserPointTable userPointTable;

  @Autowired
  private PointHistoryTable pointHistoryTable;

  @Autowired
  private PointService pointService;

  @Test
  @DisplayName("사용자 포인트 조회")
  void 사용자_포인트_조회() throws Exception {
    /**
     * HttpStatus 406 리턴하여 오류
     * 원인 : Controller에서 리턴시 APPLICATION_JSON 타입으로 리턴하지 않아서.
     * 해결방법
     * 1) 메서드에 @ResponseBody 추가 - 실패
     * 2) UserPoint에 @Getter 어노테이션 추가 => record 클래스에서는 이미 Getter가 구현되어있음
     * 그러나 내가 class로 변환하는 바람에 Getter 기능이 사라져서 JSON형식으로 변환이 불가했었음.
     * */

    // given : 사용자의 아이디와 포인트를 초기화
    long userId = 1L;
    UserPoint userPoint = userPointTable.insertOrUpdate(userId, 1000L);

    // when


    // then : JSON 형식으로 응답
    mockMvc.perform(get("/point/{id}", userId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(userPoint.id()))
        .andExpect(jsonPath("$.point").value(userPoint.point()));
  }

  @Test
  @DisplayName("사용자 포인트 거래내역 조회")
  void 사용자_포인트_거래내역_조회() throws Exception {
    // given
    long userId = 1L;
    pointService.charge(userId, 1000L);
    pointService.charge(userId, 2000L);
    pointService.use(userId, 500L);


    // when
    List<PointHistory> pointHistories = pointService.getPointHistory(userId);


    // then : JSON 형식으로 응답
    mockMvc.perform(get("/point/{id}/histories", userId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value(pointHistories.get(0).id()))
        .andExpect(jsonPath("$[0].amount").value(pointHistories.get(0).amount()))
        .andExpect(jsonPath("$[0].type").value(pointHistories.get(0).type().toString()));
  }

  @Test
  @DisplayName("포인트 충전")
  void 포인트_충전() throws Exception {
    // given
    long userId = 1L;
    UserPoint userpoint = userPointTable.selectById(userId);
    userPointTable.insertOrUpdate(userpoint.id(), 0L); // 초기화

    // when
    Long amount = 20000L;
    String requestBody = "{\"amount\":" + amount + "}";


    // then
    mockMvc.perform(patch("/point/{id}/charge", userId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(userpoint.id()))
        .andExpect(jsonPath("$.amount").value(amount));
  }

  @Test
  @DisplayName("포인트 사용")
  void 포인트_사용() throws Exception {
    // given
    long userId = 1L;
    UserPoint userpoint = userPointTable.selectById(userId);
    userPointTable.insertOrUpdate(userpoint.id(), 20000L); // 초기화

    // when
    Long amount = 10000L;
    String requestBody = "{\"amount\":" + amount + "}";


    // then
    mockMvc.perform(patch("/point/{id}/use", userId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(userpoint.id()))
        .andExpect(jsonPath("$.amount").value(amount));
  }


}
