package com.tdd.concert.domain.seat.model;

import java.time.LocalDateTime;

import com.tdd.concert.domain.concert.model.Concert;
import com.tdd.concert.domain.concert.model.ConcertSchedule;
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
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table
@Getter
@ToString
@Setter
@NoArgsConstructor
@Builder
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seatId;

    @Column(name = "seat_no")
    private long seatNo;

    @Column(name="price")
    private int price;

    @Column(name ="seat_status")
    private SeatStatus seatStatus;

    @Column(name ="temp_reserved_user_id")
    private Long tempReservedUserId;

    @Column(name ="temp_reserved_expired_at")
    private LocalDateTime tempReservedExpiredAt;

    @ManyToOne
    @JoinColumn(name ="concert_id")
    private Concert concert;

    @ManyToOne
    @JoinColumn(name ="concert_schedule_id")
    private ConcertSchedule concertSchedule;

    public void tempOccupy(Long userId) {
        this.setTempReservedUserId(userId);
        this.setTempReservedExpiredAt(LocalDateTime.now().plusMinutes(5));
        this.setSeatStatus(SeatStatus.TEMPORARY_RESERVED);
    }

    public void expire() {
        this.setTempReservedUserId(null);
        this.setTempReservedExpiredAt(null);
        this.setSeatStatus(SeatStatus.AVAILABLE);
    }

    public Seat(long seatNo, int price, SeatStatus seatStatus) {
        this.seatNo = seatNo;
        this.price = price;
        this.seatStatus = seatStatus;
    }

    public Seat(long seatId, long seatNo, int price,
                SeatStatus seatStatus, Long tempReservedUserId,
                LocalDateTime tempReservedExpiredAt,
                Concert concert, ConcertSchedule concertSchedule) {
        this.seatId = seatId;
        this.seatNo = seatNo;
        this.price = price;
        this.seatStatus = seatStatus;
        this.tempReservedUserId = tempReservedUserId;
        this.tempReservedExpiredAt = tempReservedExpiredAt;
        this.concert = concert;
        this.concertSchedule = concertSchedule;
    }
}
