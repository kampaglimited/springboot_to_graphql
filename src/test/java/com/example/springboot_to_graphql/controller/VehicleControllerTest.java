package com.example.springboot_to_graphql.controller;

import com.example.springboot_to_graphql.model.Vehicle;
import com.example.springboot_to_graphql.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VehicleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VehicleService vehicleService;

    @InjectMocks
    private VehicleController vehicleController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(vehicleController).build();
    }

    @Test
    void testGetAllVehicles() throws Exception {
        Vehicle v1 = new Vehicle();
        v1.setId(1L);
        v1.setMake("Tesla");
        v1.setModel("Model S");

        Mockito.when(vehicleService.getAllVehicles()).thenReturn(Arrays.asList(v1));

        mockMvc.perform(get("/api/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].make").value("Tesla"));
    }

    @Test
    void testGetVehicleById_Found() throws Exception {
        Vehicle v1 = new Vehicle();
        v1.setId(1L);
        v1.setMake("Tesla");

        Mockito.when(vehicleService.getVehicleById(1L)).thenReturn(Optional.of(v1));

        mockMvc.perform(get("/api/vehicles/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value("Tesla"));
    }

    @Test
    void testGetVehicleById_NotFound() throws Exception {
        Mockito.when(vehicleService.getVehicleById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/vehicles/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateVehicle() throws Exception {
        Vehicle v1 = new Vehicle();
        v1.setId(1L);
        v1.setMake("Ford");
        
        Mockito.when(vehicleService.createVehicle(any(Vehicle.class))).thenReturn(v1);

        mockMvc.perform(post("/api/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"make\": \"Ford\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void testUpdateVehicle_Success() throws Exception {
        Vehicle updated = new Vehicle();
        updated.setId(1L);
        updated.setMake("BMW");

        Mockito.when(vehicleService.updateVehicle(eq(1L), any(Vehicle.class))).thenReturn(updated);

        mockMvc.perform(put("/api/vehicles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"make\": \"BMW\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value("BMW"));
    }

    @Test
    void testUpdateVehicle_NotFound() throws Exception {
        Mockito.when(vehicleService.updateVehicle(eq(1L), any(Vehicle.class)))
                .thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(put("/api/vehicles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"make\": \"BMW\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteVehicle() throws Exception {
        mockMvc.perform(delete("/api/vehicles/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(vehicleService).deleteVehicle(1L);
    }
}

