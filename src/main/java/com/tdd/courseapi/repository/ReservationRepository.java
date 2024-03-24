package com.tdd.courseapi.repository;

import com.tdd.courseapi.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
