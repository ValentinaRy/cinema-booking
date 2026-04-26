package com.github.valentinary.cinemabooking.repository;

import com.github.valentinary.cinemabooking.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
}
