package com.example.springboot_to_graphql.service;

import com.example.springboot_to_graphql.model.Vehicle;
import com.example.springboot_to_graphql.repository.VehicleRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository repository;

    public VehicleService(VehicleRepository repository) {
        this.repository = repository;
    }

    public List<Vehicle> getAllVehicles() {
        return repository.findAll();
    }

    public Optional<Vehicle> getVehicleById(Long id) {
        return repository.findById(id);
    }

    public Vehicle createVehicle(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    public Vehicle updateVehicle(Long id, Vehicle vehicleDetails) {
        return repository.findById(id).map(vehicle -> {
            vehicle.setMake(vehicleDetails.getMake());
            vehicle.setModel(vehicleDetails.getModel());
            vehicle.setYear(vehicleDetails.getYear());
            vehicle.setVin(vehicleDetails.getVin());
            vehicle.setColor(vehicleDetails.getColor());
            vehicle.setMileage(vehicleDetails.getMileage());
            vehicle.setPrice(vehicleDetails.getPrice());
            vehicle.setEngineType(vehicleDetails.getEngineType());
            vehicle.setTransmission(vehicleDetails.getTransmission());
            vehicle.setDrivetrain(vehicleDetails.getDrivetrain());
            vehicle.setFuelType(vehicleDetails.getFuelType());
            vehicle.setDoors(vehicleDetails.getDoors());
            vehicle.setSeats(vehicleDetails.getSeats());
            vehicle.setBodyStyle(vehicleDetails.getBodyStyle());
            vehicle.setConditionStatus(vehicleDetails.getConditionStatus());
            vehicle.setInventoryStatus(vehicleDetails.getInventoryStatus());
            vehicle.setOwnerCount(vehicleDetails.getOwnerCount());
            vehicle.setHorsepower(vehicleDetails.getHorsepower());
            vehicle.setTorque(vehicleDetails.getTorque());
            vehicle.setCityMpg(vehicleDetails.getCityMpg());
            vehicle.setHighwayMpg(vehicleDetails.getHighwayMpg());
            vehicle.setWeightGvwr(vehicleDetails.getWeightGvwr());
            vehicle.setWheelbase(vehicleDetails.getWheelbase());
            return repository.save(vehicle);
        }).orElseThrow(() -> new RuntimeException("Vehicle not found with id " + id));
    }

    public void deleteVehicle(Long id) {
        repository.deleteById(id);
    }
}
