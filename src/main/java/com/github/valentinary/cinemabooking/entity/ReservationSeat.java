package com.github.valentinary.cinemabooking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"sessionId", "seatId"}
                )
        }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long reservationId;
    private Long sessionId;
    private Long seatId;
}
