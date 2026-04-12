package com.karlsson.wigellmcrental.repo;

import com.karlsson.wigellmcrental.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUsername(String username);  //for login
}
