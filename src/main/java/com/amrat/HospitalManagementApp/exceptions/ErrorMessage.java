package com.amrat.HospitalManagementApp.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ErrorMessage {
    private LocalDateTime timeStamp;
    private String error;
    private int statusCode;

    public ErrorMessage(){
        this.timeStamp = LocalDateTime.now();
    }
    public ErrorMessage(String error, int statusCode){
        this();
        this.error = error;
        this.statusCode = statusCode;
    }
}
