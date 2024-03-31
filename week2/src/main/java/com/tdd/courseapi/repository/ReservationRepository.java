package com.tdd.courseapi.repository;

import com.tdd.courseapi.domain.ReservationEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

  @Query("SELECT r FROM ReservationEntity r WHERE r.userId = :userId AND r.courseEntity.courseId = :courseId")
  ReservationEntity findByUserIdAndCourseId(@Param("userId") long userId,
                                            @Param("courseId") long courseId);

  // course_id에 해당하는 reservation의 갯수를 조회하는 메서드
  int countByCourseEntityCourseId(long courseId);

  /**
   * 비관적락 사용이유 : 데이터를 읽거나 수정하기 전에 데이터에 대한 락 획득
   * 특강신청 데이터 insert시에 락이 걸려야 차례대로 insert.
   * 그리고 총 신청내역을 읽어올 때, 락이 걸려있지 않으면 정확한 개수를 확인 어려워서.
   */
  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select r from ReservationEntity r where r.userId = ?1 AND r.courseEntity.courseId = ?2")
  ReservationEntity findByUserIdWithExclusiveLock(long userId, long courseId);

}
