package com.github.valentinary.cinemabooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sessionId;
    private Long reservedBy;
    private LocalDateTime reservedUntil;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
