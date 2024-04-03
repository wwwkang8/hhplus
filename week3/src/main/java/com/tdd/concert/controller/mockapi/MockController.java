package com.tdd.concert.controller.mockapi;

import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.dto.response.ConcertResponseDto;
import com.tdd.concert.dto.response.TokenResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MockController {

  private final MockManager mockManager;

  // 생성자 주입
  public MockController(MockManager mockManager) {
    this.mockManager = mockManager;
  }

  // 1. 대기열 추가 : 토큰이 있는지 없는지 확인하여 대기열에 추가 또는 대기 순번 보여줌
  @PostMapping("/queue")
  public ResponseEntity<TokenResponseDto> insertQueue(HttpServletRequest request) {
    TokenResponseDto response;
    String headerToken = request.getHeader("Authorization");

    if(headerToken == null) {
      // 1. 처음 접속한 사용자인지
      long userId = 131L;
      mockManager.insertWaitList(userId);

      response= new TokenResponseDto(userId, "5BAD-REFD-EEER-RIKD", 10L);
    }else {
      // 2. 이미 있는 사용자인지
      long userId = 10L;
      mockManager.insertWaitList(userId);

      response= new TokenResponseDto(userId, headerToken, 2L);
    }
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/queue/{userId}")
  public ResponseEntity<TokenResponseDto> getWaitNo(@PathVariable long userId) {

    long waitNo = mockManager.getWaitNo(userId);
    String mockToken = "5555-5555-5555-6666";

    TokenResponseDto responseDto = new TokenResponseDto(userId, mockToken,waitNo);
    return ResponseEntity.ok().body(responseDto);
  }

  @GetMapping("/concert/{concertId}")
  public ResponseEntity<ConcertResponseDto> getAvailableConcert(@PathVariable long concertId) {

      ConcertResponseDto concertResponseDto = mockManager.getAvailableConcert(concertId);

      return ResponseEntity.ok().body(concertResponseDto);
  }


  @GetMapping("/concert/{concertId}/{concertDate}")
  public ResponseEntity<ConcertResponseDto> getAvailableSeat(@PathVariable long concertId,
                                                                @PathVariable String concertDate) {

    ConcertResponseDto concertResponseDto = mockManager.getAvailableSeat(concertId, concertDate);

    return ResponseEntity.ok().body(concertResponseDto);
  }


}
