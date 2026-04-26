package com.github.valentinary.cinemabooking.repository;

import com.github.valentinary.cinemabooking.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
}
