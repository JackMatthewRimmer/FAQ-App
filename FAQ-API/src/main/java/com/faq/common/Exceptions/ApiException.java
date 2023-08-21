package com.faq.common.Exceptions;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ApiException extends RuntimeException {

    private final ApiErrorType apiErrorType;

    public ApiException(ApiErrorType apiErrorType) {
        this.apiErrorType = apiErrorType;
    }

    @Override
    public String getMessage() {
        return apiErrorType.message;
    }

    public Map<String, String> getResponseObject() {
        Map<String, String> response = new HashMap<>();
        response.put("Error", apiErrorType.message);
        return response;
    }

    public HttpStatus getStatusCode() {
        return apiErrorType.statusCode;
    }

    public enum ApiErrorType {

        // Accounts
        ACCOUNT_MISSING_EMAIL("the request was missing required field: email", HttpStatus.BAD_REQUEST),
        ACCOUNT_MISSING_PASSWORD("the request was missing required field: password", HttpStatus.BAD_REQUEST),
        ACCOUNT_INVALID_EMAIL("the request was field was invalid: email", HttpStatus.BAD_REQUEST),
        ACCOUNT_EMAIL_IN_USE("the email provided is already in use", HttpStatus.CONFLICT),
        ACCOUNT_DOES_NOT_EXIST("there is no account matching the details provided", HttpStatus.UNAUTHORIZED);

        public final String message;
        public final HttpStatus statusCode;
        ApiErrorType(String message, HttpStatus statusCode) {
            this.message = message;
            this.statusCode = statusCode;
        }
    }

}
