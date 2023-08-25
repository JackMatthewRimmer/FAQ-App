package com.faq.common.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleAccountException(ApiException ex) {
        Logger logger = LoggerFactory.getLogger(ex.getOrigin());
        logger.error("Error occurred: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getResponseObject(), ex.getStatusCode());
    }
}
