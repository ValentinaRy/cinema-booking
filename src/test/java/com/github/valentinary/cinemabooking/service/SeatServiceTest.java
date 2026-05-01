package com.github.valentinary.cinemabooking.service;

import com.github.valentinary.cinemabooking.repository.ReservationSeatRepository;
import com.github.valentinary.cinemabooking.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private ReservationSeatRepository reservationSeatRepository;
    private SeatService seatService;

    @BeforeEach
    void setUp() {
        seatService = new SeatService(seatRepository, reservationSeatRepository);
    }

    @Test
    void getSeatsEmptyList() {
        when(seatRepository.findAllBySessionId(1L)).thenReturn(Collections.emptyList());
        assertTrue(seatService.getSeatsForSession(1L).isEmpty());
    }

}