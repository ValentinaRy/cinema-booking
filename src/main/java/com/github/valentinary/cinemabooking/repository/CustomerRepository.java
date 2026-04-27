package com.github.valentinary.cinemabooking.repository;

import com.github.valentinary.cinemabooking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
