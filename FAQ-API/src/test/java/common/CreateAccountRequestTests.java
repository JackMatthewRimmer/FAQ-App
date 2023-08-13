package common;

import com.faq.common.Requests.CreateAccountRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateAccountRequestTests {

    private static final String validEmail = "test.email@foo.com";
    private static final String invalidEmail = "test.email";
    private static final String validPassword = "password";

    private CreateAccountRequest createAccountRequest;

    @BeforeEach
    void initRequest() {
        createAccountRequest = new CreateAccountRequest();
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
        assertFalse(createAccountRequest.validate());
    }

    @Test
    void validate_CreateAccountRequest_noEmail() {
        createAccountRequest.setPassword(validPassword);
        assertFalse(createAccountRequest.validate());
    }

    @Test
    void validate_CreateAccountRequest_noPassword() {
        createAccountRequest.setEmail(validEmail);
        assertFalse(createAccountRequest.validate());
    }
}
