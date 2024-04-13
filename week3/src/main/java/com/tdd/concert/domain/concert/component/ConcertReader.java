package com.tdd.concert.domain.concert.component;

import java.time.LocalDate;
import java.util.List;

import com.tdd.concert.domain.concert.infra.ConcertCoreRepositoryImpl;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.seat.model.SeatStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ConcertReader {

  private final ConcertCoreRepositoryImpl concertCoreRepository;

  public List<LocalDate> availableConcertDate(long concertId) {

    List<LocalDate> concertDateList = concertCoreRepository.availableConcertDate(concertId);

    if(concertDateList.isEmpty()) {
      throw new RuntimeException("[콘서트아이디 : " + concertId + "]콘서트 아이디에 해당하는 공연 일자가 없습니다.");
    }

    return concertDateList;
  }

  public List<Long> seatNoList(long concertId, LocalDate concertDate) {
    List<Long> seatNoList = concertCoreRepository.seatNoList(concertId,
                                                             concertDate,
                                                             SeatStatus.AVAILABLE);

    if(seatNoList.isEmpty()) {
      throw new RuntimeException("[콘서트아이디 : " + concertId + "] 예약 가능한 좌석이 없습니다.");
    }

    return seatNoList;
  }

  public Concert findConcertByConcertId(Long concertId) {
    return concertCoreRepository.findConcertByConcertId(concertId);
  }

}
