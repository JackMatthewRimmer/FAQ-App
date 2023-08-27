package com.faq.Security;

import com.faq.common.Entities.AccountEntity;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class JwtTokenUtilTests {

    private static final String VALID_EMAIL = "test-email@domain.com";
    private static final String VALID_PASSWORD = "password";

    private JwtTokenUtil jwtTokenUtil;

    private AccountEntity account;

    @BeforeEach
    public void init() {
        jwtTokenUtil = new JwtTokenUtil();
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", "secret");
    }

    @BeforeEach
    void initAccount() {
        account = new AccountEntity(VALID_EMAIL, VALID_PASSWORD);
    }

    @Test
    void testValidTokenStoresUserName() {
        String token = jwtTokenUtil.generateToken(account);
        assertEquals(account.getUsername(), jwtTokenUtil.getUsernameFromToken(token));
    }

    @Test
    void testValidTokenIsValid() {
        String token = jwtTokenUtil.generateToken(account);
        assertTrue(jwtTokenUtil.validateToken(token, account));
    }
}
