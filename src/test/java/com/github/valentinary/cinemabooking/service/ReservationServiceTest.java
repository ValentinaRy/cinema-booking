package com.github.valentinary.cinemabooking.service;

import com.github.valentinary.cinemabooking.entity.Reservation;
import com.github.valentinary.cinemabooking.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ReservationSeatRepository reservationSeatRepository;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private SeatRepository seatRepository;
    @InjectMocks
    private ReservationService reservationService;

    @Test
    void shouldCreateReservationSuccessfully() {
        Long sessionId = 1L;
        Long userId = 10L;
        List<Long> seatIds = List.of(1L, 2L);
        when(sessionRepository.existsById(sessionId)).thenReturn(true);
        when(seatRepository.existsById(anyLong())).thenReturn(true);
        when(reservationSeatRepository.findReservedBySessionId(sessionId)).thenReturn(Collections.emptyList());
        Reservation savedReservation = Reservation.builder().id(100L).build();
        when(reservationRepository.save(any())).thenReturn(savedReservation);
        Long result = reservationService.createReservation(sessionId, userId, seatIds);

        assertEquals(100L, result);
        verify(reservationRepository).save(any());
        verify(reservationSeatRepository).saveAll(any());
    }

    @Test
    void shouldThrowExceptionWhenSessionNotFound() {
        when(sessionRepository.existsById(1L)).thenReturn(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.createReservation(1L, 1L, List.of(1L)));
        assertTrue(exception.getMessage().contains("Session id not found"));
    }

    @Test
    void shouldThrowExceptionWhenSeatNotFound() {
        when(sessionRepository.existsById(1L)).thenReturn(true);
        when(seatRepository.existsById(1L)).thenReturn(true);
        when(seatRepository.existsById(2L)).thenReturn(false);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.createReservation(1L, 1L, List.of(1L, 2L)));
        assertTrue(exception.getMessage().contains("Some of the seats not found"));
    }

    @Test
    void shouldThrowExceptionWhenSeatAlreadyReserved() {
        Long sessionId = 1L;
        when(sessionRepository.existsById(sessionId)).thenReturn(true);
        when(seatRepository.existsById(anyLong())).thenReturn(true);
        SeatReservationProjection reservation = mock(SeatReservationProjection.class);
        when(reservation.getSeatId()).thenReturn(1L);
        when(reservation.isReserved(any())).thenReturn(true);
        when(reservationSeatRepository.findReservedBySessionId(sessionId)).thenReturn(List.of(reservation));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.createReservation(sessionId, 1L, List.of(1L)));
        assertTrue(exception.getMessage().contains("Seats are not available"));
    }

    @Test
    void shouldThrowExceptionWhenConstraintViolationOccurs() {
        Long sessionId = 1L;
        when(sessionRepository.existsById(sessionId)).thenReturn(true);
        when(seatRepository.existsById(anyLong())).thenReturn(true);
        when(reservationSeatRepository.findReservedBySessionId(sessionId)).thenReturn(Collections.emptyList());
        Reservation reservation = Reservation.builder().id(100L).build();
        when(reservationRepository.save(any())).thenReturn(reservation);
        when(reservationSeatRepository.saveAll(any())).thenThrow(DataIntegrityViolationException.class);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> reservationService.createReservation(sessionId, 1L, List.of(1L)));
        assertTrue(exception.getMessage().contains("already reserved"));
    }
}