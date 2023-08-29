package com.faq.common.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice
public class ApiExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleGeneralException(ApiException ex) {
        logTrace(ex);
        return new ResponseEntity<>(ex.getResponseObject(), ex.getStatusCode());
    }

    public void handleFilterLevelException(ApiException ex, HttpServletResponse response) {
        logTrace(ex);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(ex.getResponseObject());
            response.getWriter().write(json);
            response.setStatus(ex.getStatusCode().value());
            response.setContentType("application/json");

        }
        catch (Exception e) {
            logger.error("Error from filter layer exception handling", e);
        }
    }

    private void logTrace(ApiException ex) {
        Logger traceLogger = LoggerFactory.getLogger(ex.getOrigin());
        traceLogger.error("Error occurred: {}", ex.getMessage());
    }

}
