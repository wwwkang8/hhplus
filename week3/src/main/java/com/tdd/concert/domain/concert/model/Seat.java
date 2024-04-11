package com.tdd.concert.domain.concert.model;

import java.time.LocalDateTime;

import com.tdd.concert.domain.concert.status.ReservationStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@ToString
@Setter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @Column(name = "seat_no")
    private Long seatNo;

    @Column(name="price")
    private int price;

    @Column(name ="reservation_status")
    private ReservationStatus reservationStatus;

    @Column(name ="temp_reserved_user_id")
    private Long tempReservedUserId;

    @Column(name ="temp_reserved_expired_at")
    private LocalDateTime tempReservedExpiredAt;

    @ManyToOne
    @JoinColumn(name ="concert_id")
    private Concert concert;

}
