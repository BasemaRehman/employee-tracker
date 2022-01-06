package com.springboot.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface EmployeeRepository
        extends JpaRepository<Employee, Integer> {
    Boolean existsByName(String name);

    //DELETE * FROM employee WHERE name = <name>
    @Transactional
    void deleteEmployeeById(Integer id);

    @Modifying
    @Transactional
    @Query("" +
            "UPDATE Employee e " +
            "SET e.name = ?2, " +
            "e.email = ?3 " +
            "WHERE e.id = ?1"
    )
    void updateEmployeeByName(Integer Id, String name, String email);

}
