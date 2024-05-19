package com.tdd.concert.domain.outbox.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Outbox {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="event_type", nullable = false)
  private String eventType;

  @Column(name = "event_status", nullable = false)
  private OutboxStatus OutboxStatus;

  @Column(name = "payload", nullable = false, columnDefinition = "JSON")
  private String payload;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;


  public Outbox(Long id, String eventType,
                com.tdd.concert.domain.outbox.model.OutboxStatus outboxStatus,
                String payload, LocalDateTime createdAt) {
    this.id = id;
    this.eventType = eventType;
    OutboxStatus = outboxStatus;
    this.payload = payload;
    this.createdAt = createdAt;
  }

  public Outbox(OutboxStatus outboxStatus, String payload, LocalDateTime createdAt) {
    this.OutboxStatus = outboxStatus;
    this.payload = payload;
    this.createdAt = createdAt;
  }

  public Outbox() {

  }

  public Outbox(String eventType, com.tdd.concert.domain.outbox.model.OutboxStatus outboxStatus,
                String payload, LocalDateTime createdAt) {
    this.eventType = eventType;
    OutboxStatus = outboxStatus;
    this.payload = payload;
    this.createdAt = createdAt;
  }

  public static Outbox of(String eventType, OutboxStatus OutboxStatus, String payload) {

    return Outbox.builder()
            .eventType(eventType)
            .OutboxStatus(OutboxStatus)
            .payload(payload)
            .createdAt(LocalDateTime.now())
            .build();
  }

}
