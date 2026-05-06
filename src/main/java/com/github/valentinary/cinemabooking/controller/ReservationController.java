package com.github.valentinary.cinemabooking.controller;

import com.github.valentinary.cinemabooking.dto.CreateReservationRequest;
import com.github.valentinary.cinemabooking.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public Long createReservation(
            @RequestBody CreateReservationRequest request) {

        return reservationService.createReservation(request.getSessionId(), request.getUserId(), request.getSeatIds());
    }

    @PostMapping("/{id}/confirm")
    public void confirmReservation(@PathVariable Long id) {
        reservationService.confirmReservation(id);
    }

    @DeleteMapping("/{id}")
    public void cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
    }
}