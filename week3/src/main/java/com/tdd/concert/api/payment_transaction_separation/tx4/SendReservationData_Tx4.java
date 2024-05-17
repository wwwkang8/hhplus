package com.tdd.concert.api.payment_transaction_separation.tx4;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SendReservationData_Tx4 {
  public void sendData(Long userId, int price, Long seatId, Long reservationId) {

    log.info("[SendReservationData_Tx4] sendData 진입");

  }
}
