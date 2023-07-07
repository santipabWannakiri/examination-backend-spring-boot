package com.example.examination.model;

import lombok.Data;

@Data
public class RequestEditPosition {
    private String oldPosition;
    private String newPosition;


    public RequestEditPosition(String oldPosition, String newPosition) {
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }

    public RequestEditPosition() {
    }
}
