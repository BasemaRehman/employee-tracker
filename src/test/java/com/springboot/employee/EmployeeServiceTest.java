package com.springboot.employee;

import com.springboot.employee.exception.DuplicateKeyException;
import com.springboot.employee.exception.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    private EmployeeService underTest;
    private Employee basema;

    @BeforeEach
    void setUp() {
        underTest = new EmployeeService(employeeRepository);
        basema = new Employee(
                "Basema",
                "basema.rehman@spring.com",
                "Full Stack Engineer");
    }

    @AfterEach
    void tearDown() throws Exception {
        employeeRepository.deleteAll();
    }

    @Test
    void itCanGetEmployees() {
        //when
        underTest.getEmployees();
        //Then
        verify(employeeRepository).findAll();

    }

    @Test
    void itCanInsertAnEmployee() {
        //When
        underTest.insertEmployees(basema);
        //Then
        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());
        Employee captured = captor.getValue();
        assertThat(captured).isEqualTo(basema);
    }

    @Test
    void itShouldReturnAnInsertError() {
        //Given
        given(employeeRepository.existsByName(basema.getName())).willReturn(true);
        //Then
        assertThrows(DuplicateKeyException.class, () -> underTest.insertEmployees(basema));
    }

    @Test
    void itCanDeleteAnEmployee() {
        //When
        given(employeeRepository.existsById(basema.getId()))
                .willReturn(true);
        underTest.deleteEmployee(basema.getId());
        //Then
        verify(employeeRepository).deleteEmployeeById(basema.getId());
    }

    @Test
    void itShouldReturnADeleteError() {
        //When
        given(employeeRepository.existsById(basema.getId()))
                .willReturn(false);
        //Then
        assertThrows(NotFoundException.class, () -> underTest.deleteEmployee(basema.getId()));
    }

    @Test
    void itCanUpdateAnEmployee() {
        //Given
        given(employeeRepository.existsById(1))
                .willReturn(true);
        //When
        underTest.updateEmployeeByName(1, "Random", "Random");
        //Then
        verify(employeeRepository).updateEmployeeByName(1, "Random", "Random");
    }

    @Test
    void itShouldReturnAnUpdateError() {
        //When
        given(employeeRepository.existsById(basema.getId()))
                .willReturn(false);
        //Then
        assertThrows(NotFoundException.class, () -> underTest.updateEmployeeByName(basema.getId(), "name", "email"));
    }
}