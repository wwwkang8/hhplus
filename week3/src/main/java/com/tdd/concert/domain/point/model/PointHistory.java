package com.tdd.concert.domain.point.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tdd.concert.api.controller.dto.request.PointRequest;
import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.reservation.model.ReservationStatus;
import com.tdd.concert.domain.seat.model.Seat;
import com.tdd.concert.domain.user.model.User;
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

@Entity
@Table
@Getter
@ToString
@Setter
@Builder
public class PointHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long pointHistoryId;

  @Column(name="point_rscd")
  private PointRscd pointRscd;

  @Column(name="amount")
  private int amount;

  @Column(name="created_at")
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;

  public PointHistory() {

  }

  public PointHistory(long pointHistoryId, PointRscd pointRscd, int amount,
                      LocalDateTime createdAt, User user) {
    this.pointHistoryId = pointHistoryId;
    this.pointRscd = pointRscd;
    this.amount = amount;
    this.createdAt = createdAt;
    this.user = user;
  }

  public static PointHistory makePointHistory(PointRequest request) {

    return PointHistory.builder()
                       .pointRscd(request.getPointRscd())
                       .amount(request.getAmount())
                       .user(request.getUser())
                       .createdAt(LocalDateTime.now())
                       .build();

  }
}
