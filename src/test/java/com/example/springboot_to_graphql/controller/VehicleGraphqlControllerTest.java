package com.example.springboot_to_graphql.controller;

import com.example.springboot_to_graphql.model.Vehicle;
import com.example.springboot_to_graphql.service.VehicleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class VehicleGraphqlControllerTest {

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleGraphqlController vehicleGraphqlController;

    @Test
    void testGetAllVehicles() {
        Vehicle v = new Vehicle();
        v.setId(1L);
        v.setMake("Tesla");
        v.setModel("Model 3");
        v.setYear(2022);

        Mockito.when(vehicleService.getAllVehicles()).thenReturn(Collections.singletonList(v));

        List<Vehicle> list = vehicleGraphqlController.getAllVehicles();
        assertEquals(1, list.size());
        assertEquals("Tesla", list.get(0).getMake());
    }

    @Test
    void testGetVehicleById() {
        Vehicle v = new Vehicle();
        v.setId(1L);
        v.setMake("Toyota");

        Mockito.when(vehicleService.getVehicleById(1L)).thenReturn(Optional.of(v));

        Vehicle retrieved = vehicleGraphqlController.getVehicleById(1L);
        assertEquals("Toyota", retrieved.getMake());
    }

    @Test
    void testCreateVehicle() {
        Vehicle v = new Vehicle();
        v.setId(2L);
        v.setMake("Honda");

        Mockito.when(vehicleService.createVehicle(any())).thenReturn(v);

        Vehicle input = new Vehicle();
        input.setMake("Honda");
        
        Vehicle created = vehicleGraphqlController.createVehicle(input);
        assertEquals("Honda", created.getMake());
        assertEquals(2L, created.getId());
    }
}
