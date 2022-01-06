package com.springboot.employee;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-it.properties"
)
@AutoConfigureMockMvc
class EmployeeControllerTest {

    private String uriLink;
    private List<Employee> employees;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private EmployeeRepository repositoryTest;

    @BeforeEach
    void initializeVariables(){
        uriLink = "/api/v1/employee";
    }

    @Test
    void ItShouldGetAllEmployees() throws Exception{
        ResultActions resultActions = mvc.perform(get(uriLink));
        resultActions.andExpect(status().isOk());
    }

    @Test
    void ItShouldInsertAnEmployee() throws Exception {
        Employee employee = new Employee(
                "toInsert",
                "to.insert@spring.com",
                "insertjob");

        ResultActions resultActions = mvc.perform(post(uriLink)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(employee)));
        resultActions.andExpect(status().isOk());
        employees = repositoryTest.findAll();
        assertThat(employees)
                .usingElementComparatorIgnoringFields("id")
                .contains(employee);
    }

    @Test
    void ItShouldDeleteAnEmployee() throws Exception {
        Employee employee = new Employee(
                "toDelete",
                "to.delete@spring.com",
                "deletejob");
        repositoryTest.save(employee);

        employees = repositoryTest.findAll();
        assertThat(employees)
                .usingElementComparatorIgnoringFields("id")
                .contains(employee);
        Integer id = employees.get(0).getId();

         ResultActions resultActions = mvc.perform(delete("/api/v1/employee/" + id));
         resultActions.andExpect(status().isOk());

        employees = repositoryTest.findAll();
        assertThat(employees)
                .usingElementComparatorIgnoringFields("id")
                .doesNotContain(employee);

    }

    @Test
    void ItShouldUpdateAnEmployee() throws Exception {
        Employee employee = new Employee(
                "toUpdate",
                "to.update@spring.com",
                "updatejob");
        repositoryTest.save(employee);

        employees = repositoryTest.findAll();
        assertThat(employees)
                .usingElementComparatorIgnoringFields("id")
                .contains(employee);
        int id = employees.get(0).getId();

        employee.setName("updated");
        employee.setEmail("updated@spring.com");

        ResultActions resultActions = mvc.perform(put(uriLink +"/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee)));

        resultActions.andExpect(status().isOk());

        employees = repositoryTest.findAll();
        assertThat(employees)
                .usingElementComparatorIgnoringFields("id")
                .contains(employee);
        assertThat(employees.size() == 1);

    }
}