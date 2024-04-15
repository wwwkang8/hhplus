package com.tdd.concert.domain.payment.model;

import java.time.LocalDateTime;

import com.tdd.concert.domain.reservation.model.Reservation;
import com.tdd.concert.domain.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table
@Builder
public class Payment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long paymentId;

  @Column(name="pay_amount")
  private int payAmount;

  @Column(name="created_at")
  private LocalDateTime createdAt;

  @ManyToOne
  @JoinColumn(name="user_id")
  private User user;

  @OneToOne
  @JoinColumn(name="reservation_id")
  private Reservation reservation;


  public Payment() {

  }

  public Payment(long paymentId, int payAmount, LocalDateTime createdAt,
                 User user, Reservation reservation) {
    this.paymentId = paymentId;
    this.payAmount = payAmount;
    this.createdAt = createdAt;
    this.user = user;
    this.reservation = reservation;
  }

  public static Payment makePayment(int payAmount,
                                    User user,
                                    Reservation reservation) {

    return Payment.builder()
                  .payAmount(payAmount)
                  .createdAt(LocalDateTime.now())
                  .user(user)
                  .reservation(reservation)
                  .build();
  }

}
