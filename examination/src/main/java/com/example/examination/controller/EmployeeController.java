package com.example.examination.controller;


import com.example.examination.model.*;
import com.example.examination.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }


    @PostMapping("/employees")
    public Employee recordEmployee(@RequestParam("first_name") String first_name, @RequestParam("last_name") String last_name, @RequestParam("nickname") String nickname, @RequestParam("salary") Double salary, @RequestParam("address") String address, @RequestParam("position") String position) {

        return employeeService.save(new Employee(first_name, last_name, nickname, salary, Status.CURRENT, address, position));
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployee() {
        return employeeService.findAllEmployee();
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<Employee> tempEm = employeeService.findById(id);
        if (tempEm.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(tempEm);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found employee ID: " + id);
        }
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        Optional<Employee> tempEm = employeeService.findById(id);
        if (tempEm.isPresent()) {
            Employee employee = tempEm.get();
            employee.setStatus(Status.DELETE);
            employeeService.save(employee);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found employee ID: " + id);
        }
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<?> editEmployee(@PathVariable("id") int id, @RequestBody RequestEditEmployee reqEmployee) {
        Optional<Employee> tempEm = employeeService.findById(id);
        if (tempEm.isPresent()) {
            Employee employee = tempEm.get();
            employee.setFirst_name(reqEmployee.getFirst_name());
            employee.setLast_name(reqEmployee.getLast_name());
            employee.setNickname(reqEmployee.getNickname());
            employee.setAddress(reqEmployee.getAddress());
            Employee result = employeeService.save(employee);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found employee ID: " + id);
        }
    }

    @PutMapping("/employees/position/{id}")
    public ResponseEntity<?> editPosition(@PathVariable("id") int id, @RequestBody RequestEditPosition reqPosition) {
        Optional<Employee> tempEm = employeeService.findById(id);
        if (tempEm.isPresent()) {
            Employee employee = tempEm.get();
            if(employee.getPosition().equals(reqPosition.getOldPosition())){
                employee.setPosition(reqPosition.getNewPosition());
                employeeService.save(employee);
                return ResponseEntity.status(HttpStatus.OK).body("UPDATED");
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Current position is incorrect");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found employee ID: " + id);
        }
    }

    @PutMapping("/employees/salary/{id}")
    public ResponseEntity<?> editSalary(@PathVariable("id") int id, @RequestBody RequestEditSalary percentSalary) {
        if(percentSalary.getSalary() >= 0 && percentSalary.getSalary() <= 100){
            Optional<Employee> tempEm = employeeService.findById(id);
            Employee employee = tempEm.get();
            double curSalary = employee.getSalary();
            double rate = percentSalary.getSalary()/100;
            double newSalary = (rate*curSalary)+curSalary;
            employee.setSalary(newSalary);
            employeeService.save(employee);
            return ResponseEntity.status(HttpStatus.OK).body("Your Salary increase : " +rate*curSalary);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Salary % must be in range 0% - 100% ");
        }
    }


    @GetMapping("/employees/name")
    public ResponseEntity<?> findSubString  (@RequestParam("q") String str) {
        List<Employee> listEmployee = employeeService.findAllEmployee();
        List<Employee> tempResult = new ArrayList<>();
        for (int i=0; i<listEmployee.size(); i++){
            if (listEmployee.get(i).getFirst_name().contains(str)){
                tempResult.add(listEmployee.get(i));
            }
        }
        if(tempResult.size() > 0){
            return ResponseEntity.status(HttpStatus.OK).body(tempResult);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("[]");
        }
    }


    @DeleteMapping("/employees")
    public ResponseEntity<?> deleteByIds(@RequestParam("ids") String inputId) {
        List<String> inputList = Stream.of(inputId.split(",")).collect(Collectors.toList());
        List<Integer> notFound = new ArrayList<>();
        for (int i = 0; i < inputList.size(); i++) {
            int num = Integer.parseInt(inputList.get(i));
            Optional<Employee> em = employeeService.findById(num);
            if (em.isPresent()) {
                Employee employee = em.get();
                employee.setStatus(Status.DELETE);
                employeeService.save(employee);
            } else {
                notFound.add(num);
            }
        }
        if (notFound.size() > 0) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("not_found_ids: " + notFound);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}
