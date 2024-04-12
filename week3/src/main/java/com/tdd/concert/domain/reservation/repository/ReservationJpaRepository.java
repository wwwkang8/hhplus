package com.tdd.concert.domain.reservation.repository;

import com.tdd.concert.domain.reservation.model.Reservation;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationJpaRepository extends JpaRepository<Reservation, Long> {
}
