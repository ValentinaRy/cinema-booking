package com.github.valentinary.cinemabooking.repository;

import com.github.valentinary.cinemabooking.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("""
        SELECT s
        FROM Seat s
        WHERE s.hallId = (
                  SELECT ss.hallId
                  FROM Session ss
                  WHERE ss.id = :sessionId
              )
    """)
    List<Seat> findAllBySessionId(
            @Param("sessionId") Long sessionId);
}
