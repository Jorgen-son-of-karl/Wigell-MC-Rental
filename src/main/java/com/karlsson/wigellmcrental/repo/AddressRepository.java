package com.karlsson.wigellmcrental.repo;

import com.karlsson.wigellmcrental.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
