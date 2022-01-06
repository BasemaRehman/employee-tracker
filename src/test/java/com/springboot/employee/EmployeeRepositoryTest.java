package com.springboot.employee;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class EmployeeRepositoryTest {
    private Employee basema;

    @Autowired
    private EmployeeRepository underTest;

    @BeforeEach
    void setUp() {
        basema = new Employee(
                "Basema",
                "basema.rehman@spring.com",
                "Full Stack Engineer");
    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfAnEmployeeExists() {
        //Given
        underTest.save(basema);
        //When
        Boolean result = underTest.existsByName(basema.getName());
        //Then
        assertTrue(result);
    }

    @Test
    void itShouldDeleteEmployee() {
        //Given
        underTest.save(basema);
        //When
        underTest.deleteEmployeeById(basema.getId());
        //Then
        assertThat(underTest.count()).isEqualTo(0);
    }


    @Test
    void itShouldUpdateEmployee() {
        //Given
        underTest.save(basema);
        basema.setName("Matt");
        basema.setEmail("matt.smith@spring.com");
        //When
        underTest.updateEmployeeByName(1, basema.getName(), basema.getEmail());
        List<Employee> employees = underTest.findAll();
        System.out.println("SIZE " + employees.size() + "VALUE " + employees.get(0).getId() + employees.get(0).getName());
        //Then
        Assertions.assertThat(employees)
                .usingElementComparatorIgnoringFields("id")
                .contains(basema);
    }
}