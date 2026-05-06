package com.github.valentinary.cinemabooking.service;

import com.github.valentinary.cinemabooking.dto.SeatDto;
import com.github.valentinary.cinemabooking.dto.SeatStatus;
import com.github.valentinary.cinemabooking.entity.ReservationStatus;
import com.github.valentinary.cinemabooking.entity.Seat;
import com.github.valentinary.cinemabooking.repository.ReservationSeatRepository;
import com.github.valentinary.cinemabooking.repository.SeatRepository;
import com.github.valentinary.cinemabooking.repository.SeatReservationProjection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {
    @Mock
    private SeatRepository seatRepository;
    @Mock
    private ReservationSeatRepository reservationSeatRepository;
    @InjectMocks
    private SeatService seatService;

    @Test
    void getSeatsEmptyList() {
        when(seatRepository.findAllBySessionId(1L)).thenReturn(Collections.emptyList());
        assertTrue(seatService.getSeatsForSession(1L).isEmpty());
    }

    @Test
    void shouldReturnAllSeatsAsAvailable_whenNoReservations() {

        Seat seat1 = new Seat(1L, 1L, "A", "1");
        Seat seat2 = new Seat(2L, 1L, "A", "2");

        when(seatRepository.findAllBySessionId(1L))
                .thenReturn(List.of(seat1, seat2));

        when(reservationSeatRepository.findReservedBySessionId(1L))
                .thenReturn(Collections.emptyList());

        List<SeatDto> result = seatService.getSeatsForSession(1L);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(s -> s.getStatus() == SeatStatus.AVAILABLE));
    }

    @Test
    void shouldMarkSeatAsBooked_whenReservationIsDone() {

        Seat seat = new Seat(1L, 1L, "A", "1");

        SeatReservationProjection reservation = mock(SeatReservationProjection.class);
        when(reservation.getSeatId()).thenReturn(1L);
        when(reservation.getStatus()).thenReturn(ReservationStatus.DONE);

        when(seatRepository.findAllBySessionId(1L))
                .thenReturn(List.of(seat));

        when(reservationSeatRepository.findReservedBySessionId(1L))
                .thenReturn(List.of(reservation));

        List<SeatDto> result = seatService.getSeatsForSession(1L);

        assertEquals(SeatStatus.BOOKED, result.get(0).getStatus());
    }

    @Test
    void shouldMarkSeatAsBooked_whenPendingAndNotExpired() {

        Seat seat = new Seat(1L, 1L, "A", "1");

        SeatReservationProjection reservation = mock(SeatReservationProjection.class);
        when(reservation.getSeatId()).thenReturn(1L);
        when(reservation.getStatus()).thenReturn(ReservationStatus.PENDING);
        when(reservation.getReservedUntil())
                .thenReturn(LocalDateTime.now().plusMinutes(10));

        when(seatRepository.findAllBySessionId(1L))
                .thenReturn(List.of(seat));

        when(reservationSeatRepository.findReservedBySessionId(1L))
                .thenReturn(List.of(reservation));

        List<SeatDto> result = seatService.getSeatsForSession(1L);

        assertEquals(SeatStatus.BOOKED, result.get(0).getStatus());
    }

    @Test
    void shouldMarkSeatAsAvailable_whenPendingButExpired() {

        Seat seat = new Seat(1L, 1L, "A", "1");

        SeatReservationProjection reservation = mock(SeatReservationProjection.class);
        when(reservation.getStatus()).thenReturn(ReservationStatus.PENDING);
        when(reservation.getReservedUntil())
                .thenReturn(LocalDateTime.now().minusMinutes(10));

        when(seatRepository.findAllBySessionId(1L))
                .thenReturn(List.of(seat));

        when(reservationSeatRepository.findReservedBySessionId(1L))
                .thenReturn(List.of(reservation));

        List<SeatDto> result = seatService.getSeatsForSession(1L);

        assertEquals(SeatStatus.AVAILABLE, result.get(0).getStatus());
    }

    @Test
    void shouldMarkSeatAsAvailable_whenReservationCanceled() {

        Seat seat = new Seat(1L, 1L, "A", "1");

        SeatReservationProjection reservation = mock(SeatReservationProjection.class);
        when(reservation.getStatus()).thenReturn(ReservationStatus.CANCELED);

        when(seatRepository.findAllBySessionId(1L))
                .thenReturn(List.of(seat));

        when(reservationSeatRepository.findReservedBySessionId(1L))
                .thenReturn(List.of(reservation));

        List<SeatDto> result = seatService.getSeatsForSession(1L);

        assertEquals(SeatStatus.AVAILABLE, result.get(0).getStatus());
    }

}