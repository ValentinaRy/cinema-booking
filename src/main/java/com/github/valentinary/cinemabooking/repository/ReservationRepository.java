package com.github.valentinary.cinemabooking.repository;

import com.github.valentinary.cinemabooking.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
