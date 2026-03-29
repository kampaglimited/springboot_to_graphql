package com.example.springboot_to_graphql.repository;

import com.example.springboot_to_graphql.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
