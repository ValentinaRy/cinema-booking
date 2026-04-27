package com.github.valentinary.cinemabooking.service;

import com.github.valentinary.cinemabooking.entity.Session;
import com.github.valentinary.cinemabooking.repository.SessionRepository;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SessionService {
    private SessionRepository sessionRepository;

    public List<Session> findSessions(@Nullable Long hallId,
                                      @Nullable LocalDateTime startTimeFrom,
                                      @Nullable LocalDateTime startTimeTo,
                                      @Nullable String name) {
        return sessionRepository.findWithFilters(hallId, startTimeFrom,startTimeTo, name);
    }
}
