package com.example.examination.service;

import com.example.examination.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public Employee save(Employee employee);

    public List<Employee> findAllEmployee();

    public Optional<Employee> findById(int id);

    public boolean deleteById(int id);



}
