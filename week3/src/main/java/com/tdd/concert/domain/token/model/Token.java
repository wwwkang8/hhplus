package com.tdd.concert.domain.token.model;

import java.time.LocalDateTime;

import com.tdd.concert.domain.concert.status.ReservationStatus;
import com.tdd.concert.domain.token.status.ProgressStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
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

  @Column(name="created_at")
  private LocalDateTime createdAt;

  @Column(name="modified_at")
  private LocalDateTime modifiedAt;

  public Token() {
  }

  public Token(long id, String token,
               ProgressStatus progressStatus, LocalDateTime createdAt,
               LocalDateTime modifiedAt) {
    this.id = id;
    this.token = token;
    this.progressStatus = progressStatus;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }
}
