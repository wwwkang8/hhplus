package com.tdd.concert.domain.seat_optimistic.model;

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
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Builder
@Slf4j
public class SeatO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seatId;

    @Column(name = "seat_no")
    private long seatNo;

    @Column(name="price")
    private int price;

    @Column(name ="seat_status")
    private SeatStatusO seatStatusO;

    @Column(name ="temp_reserved_user_id")
    private Long tempReservedUserId;

    @Column(name ="temp_reserved_expired_at")
    private LocalDateTime tempReservedExpiredAt;

    @Version
    private Long version;

    @ManyToOne
    @JoinColumn(name ="concert_id")
    private Concert concert;

    @ManyToOne
    @JoinColumn(name ="concert_schedule_id")
    private ConcertSchedule concertSchedule;

    public void tempOccupy(Long userId) {
        log.info("[SeatO 엔티티 내부] tempOccupy 메서드 진입");

        this.setTempReservedUserId(userId);
        this.setTempReservedExpiredAt(LocalDateTime.now().plusMinutes(5));
        this.setSeatStatusO(SeatStatusO.TEMPORARY_RESERVED);

        log.info("[SeatO 엔티티 내부] tempOccupy 메서드 완료");
    }

    public void expire() {
        this.setTempReservedUserId(null);
        this.setTempReservedExpiredAt(null);
        this.setSeatStatusO(SeatStatusO.AVAILABLE);
    }


    public SeatO(long seatNo, int price, SeatStatusO seatStatusO) {
        this.seatNo = seatNo;
        this.price = price;
        this.seatStatusO = seatStatusO;
    }

    public SeatO(long seatId, long seatNo, int price,
                 SeatStatusO seatStatusO, Long tempReservedUserId,
                 LocalDateTime tempReservedExpiredAt,
                 Concert concert, ConcertSchedule concertSchedule) {
        this.seatId = seatId;
        this.seatNo = seatNo;
        this.price = price;
        this.seatStatusO = seatStatusO;
        this.tempReservedUserId = tempReservedUserId;
        this.tempReservedExpiredAt = tempReservedExpiredAt;
        this.concert = concert;
        this.concertSchedule = concertSchedule;
    }
}
