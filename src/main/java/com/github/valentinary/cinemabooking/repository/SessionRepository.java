package com.github.valentinary.cinemabooking.repository;

import com.github.valentinary.cinemabooking.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query("""
        SELECT s FROM Session s
        WHERE (:hallId IS NULL OR s.hallId = :hallId)
          AND (:from IS NULL OR s.startTime >= :from)
          AND (:to IS NULL OR s.startTime <= :to)
          AND (:name IS NULL OR s.name = :name)
    """)
    List<Session> findWithFilters(
            @Param("hallId") Long hallId,
            @Param("from") LocalDateTime startTimeFrom,
            @Param("to") LocalDateTime startTimeTo,
            @Param("name") String name
    );
}
