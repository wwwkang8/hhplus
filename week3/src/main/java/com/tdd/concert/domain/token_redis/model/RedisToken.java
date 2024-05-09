package com.tdd.concert.domain.token_redis.model;

import java.time.LocalDateTime;

import com.tdd.concert.domain.token_redis.status.ProgressStatusR;
import com.tdd.concert.domain.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
public class RedisToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="redis_token_id")
  private long redisTokenId;

  @Column(name="redis_token")
  private String redisToken;

  @Column(name="progress_status")
  private ProgressStatusR progressStatusR;

  @Column(name="created_at")
  private LocalDateTime createdAt;

  @Column(name="updated_at")
  private LocalDateTime updatedAt;

  @Column(name="expired_at")
  private LocalDateTime expiredAt;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public void setExpiredAtAndStatus(LocalDateTime expiredAt, ProgressStatusR status) {
    this.expiredAt = expiredAt;
    this.progressStatusR = status;
  }

  /** 결제처리가 완료되어 토큰이 만료처리 */
  public void expireToken() {
    this.setProgressStatusR(ProgressStatusR.FINISHED);
    this.setUpdatedAt(LocalDateTime.now());
  }

  /** 토큰 만료시각이 지나서 토큰 만료처리 */
  public void dropToken() {
    this.setProgressStatusR(ProgressStatusR.DROP);
    this.setUpdatedAt(LocalDateTime.now());
  }



}
