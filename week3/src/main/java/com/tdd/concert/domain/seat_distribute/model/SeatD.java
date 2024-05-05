package com.tdd.concert.domain.seat_distribute.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table
@Getter
@ToString
@Setter
@NoArgsConstructor
@Builder
@Slf4j
public class SeatD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seatId;

    @Column(name = "seat_no")
    private long seatNo;

    @Column(name = "concert_id")
    private long concertId;

    @Column(name = "concert_schedule")
    private LocalDate concertSchedule;

    @Column(name="price")
    private int price;

    @Column(name ="seat_status")
    private SeatStatusD seatStatusD;

    @Column(name ="temp_reserved_user_id")
    private Long tempReservedUserId;

    @Column(name ="temp_reserved_expired_at")
    private LocalDateTime tempReservedExpiredAt;


    public void  tempOccupy(Long userId) {

        if(this.getSeatStatusD() != SeatStatusD.AVAILABLE) {
            throw new RuntimeException("예약가능한 좌석이 아닙니다.. 좌석번호 : " + this.getSeatNo() + ", 좌석 상태 : " + this.getSeatStatusD());
        }

        this.setTempReservedUserId(userId);
        this.setTempReservedExpiredAt(LocalDateTime.now().plusMinutes(5));
        this.setSeatStatusD(SeatStatusD.TEMPORARY_RESERVED);

        log.info("[Seat 엔티티 내부] tempOccupy 완료. 사용자 : " + userId);
    }


    public void expire() {
        this.setTempReservedUserId(null);
        this.setTempReservedExpiredAt(null);
        this.setSeatStatusD(SeatStatusD.AVAILABLE);
    }

    public SeatD(long seatNo, int price, SeatStatusD seatStatusD) {
        this.seatNo = seatNo;
        this.price = price;
        this.seatStatusD = seatStatusD;
    }

    public SeatD(long seatId, long seatNo, long concertId, LocalDate concertSchedule, int price,
                 SeatStatusD seatStatusD, Long tempReservedUserId,
                 LocalDateTime tempReservedExpiredAt) {
        this.seatId = seatId;
        this.seatNo = seatNo;
        this.concertId = concertId;
        this.concertSchedule = concertSchedule;
        this.price = price;
        this.seatStatusD = seatStatusD;
        this.tempReservedUserId = tempReservedUserId;
        this.tempReservedExpiredAt = tempReservedExpiredAt;
    }
}
