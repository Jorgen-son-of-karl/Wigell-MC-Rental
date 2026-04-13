package com.karlsson.wigellmcrental.repo;

import com.karlsson.wigellmcrental.entities.Motorcycle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MotorcycleRepository extends JpaRepository<Motorcycle, Long> {

}
