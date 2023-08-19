package com.faq.Services;

import com.faq.common.Repositories.UserRepository;
import com.faq.common.Requests.AccountRequest;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTests {

    private static final String VALID_EMAIL = "test-email@domain.com";
    private static final String VALID_PASSWORD = "password";

    private AccountRequest accountRequest;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void initRequest() {
        this.accountRequest = new AccountRequest();
    }

    @Test
    public void validRequestTest() {
        accountRequest.setPassword(VALID_PASSWORD);
        accountRequest.setEmail(VALID_EMAIL);

        Map<String, String> token = accountService.createAccount(accountRequest);
        assertTrue(token.containsKey(("token")), "Token not present in response for creating account");
    }

}
