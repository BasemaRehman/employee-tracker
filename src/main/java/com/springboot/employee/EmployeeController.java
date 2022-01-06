package com.springboot.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.getEmployees();
    }

    @PostMapping
    public String insertEmployee(@RequestBody Employee employee){
        return employeeService.insertEmployees(employee);
    }

    @DeleteMapping(path = "{id}")
    public String deleteEmployee(@PathVariable("id") Integer id){
        return employeeService.deleteEmployee(id);
    }

    @PutMapping(path = "{id}")
    public String updateEmployee(@PathVariable("id") Integer id, @RequestBody Employee employee) {
        return employeeService.updateEmployeeByName(id, employee.getName(), employee.getEmail());
    }

}
