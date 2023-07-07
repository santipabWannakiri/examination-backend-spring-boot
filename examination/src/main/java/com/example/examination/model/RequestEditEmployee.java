package com.example.examination.model;

import lombok.Data;

@Data
public class RequestEditEmployee {

    private String first_name;
    private String last_name;
    private String nickname;
    private String address;

    public RequestEditEmployee(String first_name, String last_name, String nickname, String address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.nickname = nickname;
        this.address = address;
    }

    public RequestEditEmployee() {
    }
}
