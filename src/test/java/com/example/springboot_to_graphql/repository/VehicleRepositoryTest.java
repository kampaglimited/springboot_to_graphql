package com.example.springboot_to_graphql.repository;

import com.example.springboot_to_graphql.model.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository repository;

    @Test
    void testSaveAndFindById() {
        Vehicle v = new Vehicle();
        v.setMake("Ferrari");
        v.setModel("F8");
        v.setYear(2021);
        v.setPrice(300000.0);
        v = repository.save(v);

        Optional<Vehicle> found = repository.findById(v.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getMake()).isEqualTo("Ferrari");
    }

    @Test
    void testDelete() {
        Vehicle v = new Vehicle();
        v.setMake("Lamborghini");        v = repository.save(v);

        Long id = v.getId();
        repository.deleteById(id);

        Optional<Vehicle> found = repository.findById(id);
        assertThat(found).isEmpty();
    }
}
