package com.faq.Services;

import com.faq.Api;
import com.faq.common.Entities.AccountEntity;
import com.faq.common.Exceptions.ApiException;
import com.faq.common.Repositories.UserRepository;
import com.faq.common.Requests.AccountRequest;

import com.faq.common.Util.UserPasswordEncoder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTests {

    private static final String VALID_EMAIL = "test-email@domain.com";
    private static final String VALID_PASSWORD = "password";

    private AccountRequest accountRequest;

    @Mock
    private UserRepository userRepository;

    @Mock
    UserPasswordEncoder userPasswordEncoder;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void initRequest() {
        this.accountRequest = new AccountRequest();
    }

    @Test
    public void createAccountWithValidRequestTest() {
        accountRequest.setPassword(VALID_PASSWORD);
        accountRequest.setEmail(VALID_EMAIL);

        Map<String, String> token = accountService.createAccount(accountRequest);
        assertTrue(token.containsKey(("token")), "Token not present in response for creating account");
    }

    @Test
    void createAccountWithNoEmailTest() {
        accountRequest.setPassword(VALID_PASSWORD);

        ApiException apiException = assertThrows
                (ApiException.class, () -> accountService.createAccount(accountRequest));

        assertEquals(apiException.getApiErrorType(), ApiException.ApiErrorType.ACCOUNT_MISSING_EMAIL);
    }

    @Test
    void createAccountWithNoPasswordTest() {
        accountRequest.setEmail(VALID_EMAIL);

        ApiException apiException = assertThrows
                (ApiException.class, () -> accountService.createAccount(accountRequest));

        assertEquals(apiException.getApiErrorType(), ApiException.ApiErrorType.ACCOUNT_MISSING_PASSWORD);
    }

    @Test
    void createAccountWithEmailAlreadyInUseTest() {
        accountRequest.setPassword(VALID_PASSWORD);
        accountRequest.setEmail(VALID_EMAIL);

        when(userRepository.existsByEmail(accountRequest.getEmail())).thenReturn(true);

        ApiException apiException = assertThrows
                (ApiException.class, () -> accountService.createAccount(accountRequest));

        assertEquals(apiException.getApiErrorType(), ApiException.ApiErrorType.ACCOUNT_EMAIL_IN_USE);
    }

    @Test
    void loginWithValidAccountRequestTest() {
        accountRequest.setEmail(VALID_EMAIL);
        accountRequest.setPassword(VALID_PASSWORD);

        when(userPasswordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(new AccountEntity(VALID_EMAIL, VALID_PASSWORD));

        Map<String, String> token = accountService.loginAccount(accountRequest);
        assertTrue(token.containsKey(("token")), "Token not present in response for creating account");
    }

    @Test
    void loginWithAccountThatDoesNotExistTest() {
        accountRequest.setEmail(VALID_EMAIL);
        accountRequest.setPassword(VALID_PASSWORD);

        when(userPasswordEncoder.encode(Mockito.any(CharSequence.class))).thenReturn("password");

        when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(null);

        ApiException apiException = assertThrows
                (ApiException.class, () -> accountService.loginAccount(accountRequest));

        assertEquals(apiException.getApiErrorType(), ApiException.ApiErrorType.ACCOUNT_DOES_NOT_EXIST);
    }
}
