package com.example.examination;

import com.example.examination.model.Employee;
import com.example.examination.model.Status;
import com.example.examination.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExaminationApplication {

	private EmployeeService employeeService;
	@Autowired
	public ExaminationApplication(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ExaminationApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLiner(String[] args) {
		return runner -> {
			createMultipleStudents();
		};
	}

	private void createMultipleStudents() {
		Employee em1  = new Employee("SOMCHAI", "WANLOP", "WAN",40000.00, Status.CURRENT,"399/1","Manager");
		Employee em2  = new Employee("SOMYOT", "RUDEE", "SOM",50000.00, Status.CURRENT,"399/2","PM");
		Employee em3  = new Employee("SOMSREE", "MEEMAK", "MEE",90000.00, Status.CURRENT,"399/3","SA");

		employeeService.save(em1);
		employeeService.save(em2);
		employeeService.save(em3);
	}

}
