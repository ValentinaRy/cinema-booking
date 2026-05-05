package com.github.valentinary.cinemabooking.dto;

import lombok.Data;
import java.util.List;

@Data
public class CreateReservationRequest {
    private Long sessionId;
    private Long userId;
    private List<Long> seatIds;
}