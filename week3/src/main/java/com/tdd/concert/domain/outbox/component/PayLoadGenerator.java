package com.tdd.concert.domain.outbox.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tdd.concert.domain.outbox.model.PayLoad;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PayLoadGenerator {

  private final ObjectMapper objectMapper;

  public PayLoadGenerator() {
    this.objectMapper = new ObjectMapper();
  }

  public String generatePayload(long userId, long seatId, long reservationId) {
    PayLoad payload = new PayLoad(userId, seatId, reservationId);
    try {
      return objectMapper.writeValueAsString(payload);
    } catch (JsonProcessingException e) {
      // JSON 변환 오류 처리
      e.printStackTrace();
      return null;
    }
  }

}
