package com.github.valentinary.cinemabooking.controller;

import com.github.valentinary.cinemabooking.dto.SeatDto;
import com.github.valentinary.cinemabooking.entity.Session;
import com.github.valentinary.cinemabooking.service.SeatService;
import com.github.valentinary.cinemabooking.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sessions")
@AllArgsConstructor
public class SessionController {
    private final SessionService sessionService;
    private final SeatService seatService;

    @GetMapping
    List<Session> findSessions(
            @RequestParam(required = false) Long hallId,
            @RequestParam(required = false) LocalDateTime startTimeFrom,
            @RequestParam(required = false) LocalDateTime startTimeTo,
            @RequestParam(required = false) String name
            ) {
        return sessionService.findSessions(hallId,startTimeFrom, startTimeTo, name);
    }

    @GetMapping("/{sessionId}/seats")
    public List<SeatDto> getSeats(@PathVariable Long sessionId) {
        return seatService.getSeatsForSession(sessionId);
    }
}
