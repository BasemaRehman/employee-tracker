package com.springboot.employee;

import org.springframework.lang.NonNull;

import javax.persistence.*;

//Create a model of the employee to be sent to the database. Each employee consists of a name and id
//Uses a builder to create the employee structure
@Entity
@Table
public class Employee {
    @Id
    @SequenceGenerator(
            name = "employee_sequence",
            sequenceName = "employee_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employee_sequence"
    )
    private int id;
    private String name;
    private String email;
    private String jobTitle;

    public Employee(){
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Employee(String name, String email){
        this.name = name;
        this.email = email;
    }

    public Employee(String name, String email, String jobTitle){
        this.name = name;
        this.email = email;
        this.jobTitle = jobTitle;
    }


}
