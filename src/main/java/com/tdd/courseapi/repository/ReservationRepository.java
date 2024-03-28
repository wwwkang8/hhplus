package com.tdd.courseapi.repository;

import com.tdd.courseapi.domain.ReservationEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

  ReservationEntity findByUserId(long userId);

  /**
   * 비관적락 사용이유 : 데이터를 읽거나 수정하기 전에 데이터에 대한 락 획득
   * 특강신청 데이터 insert시에 락이 걸려야 차례대로 insert.
   * 그리고 총 신청내역을 읽어올 때, 락이 걸려있지 않으면 정확한 개수를 확인 어려워서.
   * */
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select r from ReservationEntity r where r.userId = ?1")
  ReservationEntity findByUserIdWithExclusiveLock(long userId);

}
