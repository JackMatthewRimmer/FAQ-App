package com.faq.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleAccountException(ApiException ex) {
        return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
    }

}
