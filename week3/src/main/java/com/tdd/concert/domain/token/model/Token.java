package com.tdd.concert.domain.token.model;

import java.time.LocalDateTime;

import com.tdd.concert.domain.token.status.ProgressStatus;
import com.tdd.concert.domain.user.model.User;
import com.tdd.concert.api.controller.dto.response.TokenResponse;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@Entity
@Table
public class Token {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="token_id")
  private long id;

  @Column(name="token")
  private String token;

  @Column(name="progress_status")
  private ProgressStatus progressStatus;

  @Column(name="wait_no")
  private long waitNo;

  @Column(name="created_at")
  private LocalDateTime createdAt;

  @Column(name="updated_at")
  private LocalDateTime updatedAt;

  @Column(name="expired_at")
  private LocalDateTime expiredAt;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public void setExpiredAtAndStatus(LocalDateTime expiredAt, ProgressStatus status) {
    this.expiredAt = expiredAt;
    this.progressStatus = status;
  }


  public Token() {
  }



  public Token(long id, String token, ProgressStatus progressStatus, long waitNo,
               LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime expiredAt,
               User user) {
    this.id = id;
    this.token = token;
    this.progressStatus = progressStatus;
    this.waitNo = waitNo;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.expiredAt = expiredAt;
    this.user = user;
  }

  public Token(long id, String token,
               ProgressStatus progressStatus, long waitNo, LocalDateTime createdAt,
               LocalDateTime expiredAt, User user) {
    this.id = id;
    this.token = token;
    this.progressStatus = progressStatus;
    this.waitNo = waitNo;
    this.createdAt = createdAt;
    this.expiredAt = expiredAt;
    this.user = user;
  }

  public Token(User user, String token, long waitNo, ProgressStatus status) {
    this.user = user;
    this.token = token;
    this.waitNo = waitNo;
    this.progressStatus = status;
  }

  public TokenResponse to(long userId, String token, long waitNo) {
    return new TokenResponse(userId, token, waitNo);
  }

  /** 결제처리가 완료되어 토큰이 만료처리 */
  public void expireToken() {
    this.setProgressStatus(ProgressStatus.FINISHED);
    this.setUpdatedAt(LocalDateTime.now());
  }

  /** 토큰 만료시각이 지나서 토큰 만료처리 */
  public void dropToken() {
    this.setProgressStatus(ProgressStatus.DROP);
    this.setUpdatedAt(LocalDateTime.now());
  }
}
