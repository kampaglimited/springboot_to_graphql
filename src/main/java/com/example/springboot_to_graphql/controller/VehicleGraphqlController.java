package com.example.springboot_to_graphql.controller;

import com.example.springboot_to_graphql.model.Vehicle;
import com.example.springboot_to_graphql.service.VehicleService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VehicleGraphqlController {

    private final VehicleService service;

    public VehicleGraphqlController(VehicleService service) {
        this.service = service;
    }

    @QueryMapping
    public List<Vehicle> getAllVehicles() {
        return service.getAllVehicles();
    }

    @QueryMapping
    public Vehicle getVehicleById(@Argument Long id) {
        return service.getVehicleById(id).orElse(null);
    }

    @MutationMapping
    public Vehicle createVehicle(@Argument Vehicle vehicle) {
        return service.createVehicle(vehicle);
    }

    @MutationMapping
    public Vehicle updateVehicle(@Argument Long id, @Argument Vehicle vehicle) {
        return service.updateVehicle(id, vehicle);
    }

    @MutationMapping
    public Boolean deleteVehicle(@Argument Long id) {
        try {
            service.deleteVehicle(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
