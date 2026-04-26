package com.github.valentinary.cinemabooking.repository;

import com.github.valentinary.cinemabooking.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
}
