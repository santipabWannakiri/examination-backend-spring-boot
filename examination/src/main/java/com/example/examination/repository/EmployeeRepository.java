package com.example.examination.repository;

import com.example.examination.model.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("EmployeeRepository")
public interface EmployeeRepository extends CrudRepository<Employee,Integer> {
}
