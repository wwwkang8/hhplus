package com.tdd.concert.domain.token.repository;

import com.tdd.concert.domain.token.model.Token;
import com.tdd.concert.domain.token.status.ProgressStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<Token, Long> {

  @Query("SELECT COALESCE(MAX(t.waitNo),0)+1 FROM Token t")
  public Long selectNextWaitNo();

  @Query("SELECT COALESCE(COUNT(t.progressStatus), 0) FROM Token t WHERE t.progressStatus = :progressStatus")
  public Long getCurrentReservationOngoingCount(@Param("progressStatus") ProgressStatus progressStatus);

  public Token findTokenByToken(String token);

}
