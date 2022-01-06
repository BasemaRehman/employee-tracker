package com.springboot.employee;

import com.springboot.employee.exception.DuplicateKeyException;
import com.springboot.employee.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepo;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public List<Employee> getEmployees(){
        return employeeRepo.findAll();
    }

    public String insertEmployees(Employee employee){
        if(employeeRepo.existsByName(employee.getName())){
            throw new DuplicateKeyException("Name already exists");
        }
        employeeRepo.save(employee);
        return "Insert Completed";
    }

    public String deleteEmployee(Integer id){
        Boolean findEmployee = employeeRepo.existsById(id);
        if(!findEmployee){
            throw new NotFoundException("No employee exists with id " + id);
        }
        employeeRepo.deleteEmployeeById(id);
        return "Deletion complete";
    }

    public String updateEmployeeByName(Integer id, String newName, String email){
        Boolean findEmployee = employeeRepo.existsById(id);
        if(!findEmployee){
            throw new NotFoundException("No employee exists with id " + id);
        }
        employeeRepo.updateEmployeeByName(id, newName, email);
        return "Update Complete";
    }
}
