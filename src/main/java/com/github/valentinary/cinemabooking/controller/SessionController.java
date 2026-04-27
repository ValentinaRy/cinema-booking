package com.github.valentinary.cinemabooking.controller;

import com.github.valentinary.cinemabooking.entity.Session;
import com.github.valentinary.cinemabooking.service.SessionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/sessions")
@AllArgsConstructor
public class SessionController {
    private final SessionService sessionService;

    @GetMapping
    List<Session> findSessions(
            @RequestParam(required = false) Long hallId,
            @RequestParam(required = false) LocalDateTime startTimeFrom,
            @RequestParam(required = false) LocalDateTime startTimeTo,
            @RequestParam(required = false) String name
            ) {
        return sessionService.findSessions(hallId,startTimeFrom, startTimeTo, name);
    }
}
