package com.faq.common.Exceptions;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class ApiException extends RuntimeException {

    private final ApiErrorType apiErrorType;

    private final Class<?> origin;

    public ApiException(ApiErrorType apiErrorType, Class<?> origin) {
        this.apiErrorType = apiErrorType;
        this.origin = origin;
    }

    @Override
    public String getMessage() {
        return this.apiErrorType.message;
    }

    public Map<String, String> getResponseObject() {
        Map<String, String> response = new HashMap<>();
        response.put("Error", apiErrorType.message);
        return response;
    }

    public ApiErrorType getApiErrorType() {
        return apiErrorType;
    }

    public HttpStatus getStatusCode() {
        return apiErrorType.statusCode;
    }

    public Class<?> getOrigin() {
        return this.origin;
    }

    public enum ApiErrorType {

        // Accounts
        ACCOUNT_MISSING_EMAIL("the request was missing required field: email", HttpStatus.BAD_REQUEST),
        ACCOUNT_MISSING_PASSWORD("the request was missing required field: password", HttpStatus.BAD_REQUEST),
        ACCOUNT_INVALID_EMAIL("the request was field was invalid: email", HttpStatus.BAD_REQUEST),
        ACCOUNT_EMAIL_IN_USE("the email provided is already in use", HttpStatus.CONFLICT),

        ACCOUNT_PASSWORD_INVALID("the email provided is already in use", HttpStatus.BAD_REQUEST),
        ACCOUNT_DOES_NOT_EXIST("there is no account matching the details provided", HttpStatus.BAD_REQUEST),

        // JWT AUTH

        TOKEN_IS_EXPIRED("the provided token was expired", HttpStatus.UNAUTHORIZED),

        TOKEN_DOES_NOT_BEGIN_WITH_BEARER("the provded token did not begin with Bearer", HttpStatus.UNAUTHORIZED),

        TOKEN_IS_INVALID("the token could not be extracted from the header", HttpStatus.UNAUTHORIZED);



        public final String message;
        public final HttpStatus statusCode;
        ApiErrorType(String message, HttpStatus statusCode) {
            this.message = message;
            this.statusCode = statusCode;
        }
    }

}
