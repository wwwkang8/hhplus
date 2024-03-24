package com.tdd.courseapi.common;

import com.tdd.courseapi.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationWriter {

  private final ReservationRepository reservationRepository;

}
