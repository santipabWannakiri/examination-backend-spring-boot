package com.example.examination.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String first_name;
    private String last_name;
    private String nickname;
    private double salary;
    private Status status;
    private String address;
    private String position;

    public Employee() {
    }

    public Employee(String first_name, String last_name, String nickname, double salary, Status status, String address, String position) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.nickname = nickname;
        this.salary = salary;
        this.status = status;
        this.address = address;
        this.position = position;
    }
}
