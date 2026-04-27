package com.github.valentinary.cinemabooking.component;

import com.github.valentinary.cinemabooking.entity.*;
import com.github.valentinary.cinemabooking.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final HallRepository hallRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationSeatRepository reservationSeatRepository;
    private final SeatRepository seatRepository;
    private final SessionRepository sessionRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) {
        Hall hallK = hallRepository.save(Hall.builder()
                .name("Hall K")
                .build());
        Seat a01 = seatRepository.save(Seat.builder()
                .hallId(hallK.getId())
                .seatRow("A")
                .seatNum("01")
                .build());
        Seat a02 = seatRepository.save(Seat.builder()
                .hallId(hallK.getId())
                .seatRow("A")
                .seatNum("02")
                .build());
        Seat b01 = seatRepository.save(Seat.builder()
                .hallId(hallK.getId())
                .seatRow("B")
                .seatNum("01")
                .build());
        Seat b02 = seatRepository.save(Seat.builder()
                .hallId(hallK.getId())
                .seatRow("B")
                .seatNum("02")
                .build());
        Session awesomeMovie = sessionRepository.save(Session.builder()
                .name("Awesome movie")
                .hallId(hallK.getId())
                .startTime(LocalDateTime.of(2026, 6, 26, 6, 26))
                .build());
        Session funnyMovie = sessionRepository.save(Session.builder()
                .name("Funny movie")
                .hallId(hallK.getId())
                .startTime(LocalDateTime.of(2026, 6, 26, 6, 26))
                .build());
        Customer customerOne = customerRepository.save(Customer.builder()
                .name("User One")
                .build());
        Customer customerTwo = customerRepository.save(Customer.builder()
                .name("User Two")
                .build());
        Reservation reservation1 = reservationRepository.save(Reservation.builder()
                .sessionId(awesomeMovie.getId())
                .reservedBy(customerOne.getId())
                .reservedUntil(LocalDateTime.now().plusHours(1))
                .status(ReservationStatus.PENDING)
                .build());
        reservationSeatRepository.save(ReservationSeat.builder()
                .reservationId(reservation1.getId())
                .seatId(a01.getId())
                .build());
        reservationSeatRepository.save(ReservationSeat.builder()
                .reservationId(reservation1.getId())
                .seatId(a02.getId())
                .build());
        Reservation reservation2 = reservationRepository.save(Reservation.builder()
                .sessionId(awesomeMovie.getId())
                .reservedBy(customerTwo.getId())
                .reservedUntil(LocalDateTime.now().minusHours(1))
                .status(ReservationStatus.DONE)
                .build());
        reservationSeatRepository.save(ReservationSeat.builder()
                .reservationId(reservation2.getId())
                .seatId(b01.getId())
                .build());
        Reservation reservation3 = reservationRepository.save(Reservation.builder()
                .sessionId(funnyMovie.getId())
                .reservedBy(customerTwo.getId())
                .reservedUntil(LocalDateTime.now().minusHours(1))
                .status(ReservationStatus.CANCELED)
                .build());
        reservationSeatRepository.save(ReservationSeat.builder()
                .reservationId(reservation3.getId())
                .seatId(a01.getId())
                .build());

        Reservation reservation4 = reservationRepository.save(Reservation.builder()
                .sessionId(awesomeMovie.getId())
                .reservedBy(customerOne.getId())
                .reservedUntil(LocalDateTime.now().plusHours(1))
                .status(ReservationStatus.DONE)
                .build());
        reservationSeatRepository.save(ReservationSeat.builder()
                .reservationId(reservation4.getId())
                .seatId(a01.getId())
                .build());
        reservationSeatRepository.save(ReservationSeat.builder()
                .reservationId(reservation4.getId())
                .seatId(a02.getId())
                .build());
    }
}
