package com.example.springboot_to_graphql.service;

import com.example.springboot_to_graphql.model.Vehicle;
import com.example.springboot_to_graphql.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository repository;

    @InjectMocks
    private VehicleService service;

    @Test
    void testGetAllVehicles() {
        Vehicle v1 = new Vehicle();
        v1.setMake("Toyota");
        Vehicle v2 = new Vehicle();
        v2.setMake("Honda");

        Mockito.when(repository.findAll()).thenReturn(Arrays.asList(v1, v2));

        List<Vehicle> list = service.getAllVehicles();
        assertEquals(2, list.size());
        assertEquals("Toyota", list.get(0).getMake());
        assertEquals("Honda", list.get(1).getMake());
    }

    @Test
    void testGetVehicleById_Exists() {
        Vehicle v = new Vehicle();
        v.setId(10L);
        v.setMake("Mazda");

        Mockito.when(repository.findById(10L)).thenReturn(Optional.of(v));

        Optional<Vehicle> result = service.getVehicleById(10L);
        assertTrue(result.isPresent());
        assertEquals("Mazda", result.get().getMake());
    }

    @Test
    void testGetVehicleById_NotExists() {
        Mockito.when(repository.findById(99L)).thenReturn(Optional.empty());

        Optional<Vehicle> result = service.getVehicleById(99L);
        assertFalse(result.isPresent());
    }

    @Test
    void testCreateVehicle() {
        Vehicle v = new Vehicle();
        v.setMake("Subaru");

        Mockito.when(repository.save(any(Vehicle.class))).thenReturn(v);

        Vehicle created = service.createVehicle(v);
        assertNotNull(created);
        assertEquals("Subaru", created.getMake());
    }

    @Test
    void testUpdateVehicle_Exists() {
        Vehicle existing = new Vehicle();
        existing.setId(1L);
        existing.setMake("Old Make");
        existing.setModel("Old Model");

        Vehicle details = new Vehicle();
        details.setMake("New Make");
        details.setModel("New Model");

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(repository.save(any(Vehicle.class))).thenReturn(existing); // existing is updated

        Vehicle updated = service.updateVehicle(1L, details);
        assertEquals("New Make", updated.getMake());
        assertEquals("New Model", updated.getModel());
    }

    @Test
    void testUpdateVehicle_NotFound_ThrowsException() {
        Vehicle details = new Vehicle();
        details.setMake("New Make");

        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.updateVehicle(1L, details));
    }

    @Test
    void testDeleteVehicle() {
        service.deleteVehicle(1L);
        Mockito.verify(repository).deleteById(1L);
    }
}
