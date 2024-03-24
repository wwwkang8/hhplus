package com.tdd.courseapi.repository;

import com.tdd.courseapi.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

  ReservationEntity findByUserId(long userId);

}
