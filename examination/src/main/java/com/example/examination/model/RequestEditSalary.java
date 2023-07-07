package com.example.examination.model;

import lombok.Data;

@Data
public class RequestEditSalary {

    private double salary;

    public RequestEditSalary(double salary) {
        this.salary = salary;
    }

    public RequestEditSalary() {
    }
}
