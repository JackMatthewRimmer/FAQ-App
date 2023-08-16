package com.faq.common;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

public class ApiException extends RuntimeException {

    private final ApiErrorType apiErrorType;

    public ApiException(ApiErrorType apiErrorType) {
        this.apiErrorType = apiErrorType;
    }

    public String getMessage() {
        return apiErrorType.message;
    }

    public HttpStatus getStatusCode() {
        return apiErrorType.statusCode;
    }

    public enum ApiErrorType {
        // Accounts
        ACCOUNT_MISSING_EMAIL("Error: the request was missing required field: email", HttpStatus.BAD_REQUEST),
        ACCOUNT_MISSING_PASSWORD("Error: the request was missing required field: password", HttpStatus.BAD_REQUEST),
        ACCOUNT_INVALID_EMAIL("Error: the request was field was invalid: email", HttpStatus.BAD_REQUEST);

        public final String message;
        public final HttpStatus statusCode;
        private ApiErrorType(String message, HttpStatus statusCode) {
            this.message = message;
            this.statusCode = statusCode;
        }
    }

}
