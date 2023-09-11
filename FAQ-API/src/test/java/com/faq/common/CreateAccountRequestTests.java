package com.faq.common;

import com.faq.common.exceptions.ApiException;
import com.faq.common.requests.AccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class CreateAccountRequestTests {

    private static final String validEmail = "test.email@foo.com";
    private static final String invalidEmail = "test.email";
    private static final String validPassword = "password";

    private AccountRequest createAccountRequest;

    @BeforeEach
    void initRequest() {
        createAccountRequest = new AccountRequest();
    }

    @Test
    void validate_CreateAccountRequest_success() {

        createAccountRequest.setEmail(validEmail);
        createAccountRequest.setPassword(validPassword);
        assertTrue(createAccountRequest.validate());
    }

    @Test
    void validate_CreateAccountRequest_invalidEmail() {
        createAccountRequest.setEmail(invalidEmail);
        createAccountRequest.setPassword(validPassword);
        assertThrows(ApiException.class, () -> createAccountRequest.validate());
    }

    @Test
    void validate_CreateAccountRequest_noEmail() {
        createAccountRequest.setPassword(validPassword);
        assertThrows(ApiException.class, () -> createAccountRequest.validate());
    }

    @Test
    void validate_CreateAccountRequest_noPassword() {
        createAccountRequest.setEmail(validEmail);
        assertThrows(ApiException.class, () -> createAccountRequest.validate());
    }
}
