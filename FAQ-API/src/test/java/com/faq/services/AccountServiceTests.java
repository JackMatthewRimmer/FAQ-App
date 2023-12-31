package com.faq.services;

import com.faq.security.JwtTokenUtil;
import com.faq.common.entities.AccountEntity;
import com.faq.common.exceptions.ApiException;
import com.faq.common.repositories.AccountRepository;
import com.faq.common.requests.AccountRequest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTests {

    private static final String VALID_EMAIL = "test-email@domain.com";
    private static final String VALID_PASSWORD = "password";

    private AccountRequest accountRequest;

    @Mock
    private AccountRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void initRequest() {
        this.accountRequest = new AccountRequest();
    }

    @Test
    void createAccountWithValidRequestTest() {
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

        assertEquals(ApiException.ApiErrorType.ACCOUNT_MISSING_EMAIL, apiException.getApiErrorType());
    }

    @Test
    void createAccountWithNoPasswordTest() {
        accountRequest.setEmail(VALID_EMAIL);

        ApiException apiException = assertThrows
                (ApiException.class, () -> accountService.createAccount(accountRequest));

        assertEquals(ApiException.ApiErrorType.ACCOUNT_MISSING_PASSWORD, apiException.getApiErrorType());
    }

    @Test
    void createAccountWithEmailAlreadyInUseTest() {
        accountRequest.setPassword(VALID_PASSWORD);
        accountRequest.setEmail(VALID_EMAIL);

        when(userRepository.existsByEmail(accountRequest.getEmail())).thenReturn(true);

        ApiException apiException = assertThrows
                (ApiException.class, () -> accountService.createAccount(accountRequest));

        assertEquals(ApiException.ApiErrorType.ACCOUNT_EMAIL_IN_USE, apiException.getApiErrorType());
    }

    @Test
    void loginWithValidAccountRequestTest() {
        accountRequest.setEmail(VALID_EMAIL);
        accountRequest.setPassword(VALID_PASSWORD);

        when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);
        when(userRepository.findByEmail(VALID_EMAIL)).
                thenReturn(Optional.of(new AccountEntity(VALID_EMAIL, VALID_PASSWORD)));

        Map<String, String> token = accountService.loginAccount(accountRequest);
        assertTrue(token.containsKey(("token")), "Token not present in response for creating account");
    }

    @Test
    void loginWithAccountThatDoesNotExistTest() {
        accountRequest.setEmail(VALID_EMAIL);
        accountRequest.setPassword(VALID_PASSWORD);

        when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.empty());

        ApiException apiException = assertThrows
                (ApiException.class, () -> accountService.loginAccount(accountRequest));

        assertEquals(ApiException.ApiErrorType.ACCOUNT_DOES_NOT_EXIST, apiException.getApiErrorType());
    }

    @Test
    void loginWithInvalidPasswordTest() {
        accountRequest.setEmail(VALID_EMAIL);
        accountRequest.setPassword(VALID_PASSWORD);

        when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(false);
        when(userRepository.findByEmail(VALID_EMAIL)).
                thenReturn(Optional.of(new AccountEntity(VALID_EMAIL, VALID_PASSWORD)));

        ApiException apiException = assertThrows
                (ApiException.class, () -> accountService.loginAccount(accountRequest));

        assertEquals(ApiException.ApiErrorType.ACCOUNT_PASSWORD_INVALID, apiException.getApiErrorType());
    }

}
