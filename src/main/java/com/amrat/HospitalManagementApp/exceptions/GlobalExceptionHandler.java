package com.amrat.HospitalManagementApp.exceptions;

import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorMessage> handleAuthenticationException(AuthenticationException exception){
        ErrorMessage errorMessage = new ErrorMessage("Authentication Failed: "+ exception.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.ok(errorMessage);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> handleIllegalArgumentException(IllegalArgumentException exception){
        ErrorMessage errorMessage = new ErrorMessage("Duplicate User: "+ exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.ok(errorMessage);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception exception){
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.ok(errorMessage);
    }

}
