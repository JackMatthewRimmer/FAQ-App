package com.faq.Services;

import com.faq.Security.JwtTokenUtil;
import com.faq.common.Entities.AccountEntity;
import com.faq.common.Exceptions.ApiException;
import com.faq.common.Repositories.AccountRepository;
import com.faq.common.Requests.AccountRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.faq.common.Exceptions.ApiException.ApiErrorType.*;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    public Map<String, String> createAccount(@NonNull AccountRequest accountRequest)
            throws ApiException {

        accountRequest.validate();
        verifyEmailNotInUse(accountRequest.getEmail());

        String hashedPassword = passwordEncoder.encode(accountRequest.getPassword());

        AccountEntity accountEntity = new AccountEntity(accountRequest.getEmail(), hashedPassword);

        this.accountRepository.save(accountEntity);

        String token = jwtTokenUtil.generateToken(accountEntity);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        logger.info("Account has been created for {}", accountRequest.getEmail());

        return response;
    }

    public Map<String, String> loginAccount(@NonNull AccountRequest accountRequest)
            throws ApiException {

        accountRequest.validate();

        AccountEntity accountEntity = new AccountEntity(accountRequest);

        verifyAccountExists(accountEntity);

        Map<String, String> response = new HashMap<>();

        String token = jwtTokenUtil.generateToken(accountEntity);

        response.put("token", token);

        logger.info("Account successfully logged in for {}",
                accountRequest.getEmail());

        return response;
    }

    private void verifyEmailNotInUse(@NonNull String email) {

        boolean emailInUse = accountRepository.existsByEmail(email);

        if (emailInUse) {
            throw new ApiException(ACCOUNT_EMAIL_IN_USE, AccountService.class);
        }
    }

    private void verifyAccountExists(@NonNull AccountEntity account) {

        Optional<AccountEntity> queryResult = accountRepository.findByEmail(account.getEmail());

        boolean passwordsMatch = queryResult
                .map(databaseAccount ->
                        passwordEncoder.matches(account.getPassword(), databaseAccount.getPassword()))
                .orElseThrow(() ->
                        new ApiException(ACCOUNT_DOES_NOT_EXIST, AccountService.class));

        if (!passwordsMatch) {
            throw new ApiException(ACCOUNT_PASSWORD_INVALID, AccountService.class);
        }
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return accountRepository.findByEmail(username)
                .orElseThrow(() ->
                        new ApiException(ACCOUNT_DOES_NOT_EXIST, AccountService.class));
    }
}
