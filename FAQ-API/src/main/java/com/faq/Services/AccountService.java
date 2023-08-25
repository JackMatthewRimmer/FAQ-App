package com.faq.Services;

import com.faq.JwtTokenUtil;
import com.faq.common.Entities.AccountEntity;
import com.faq.common.Exceptions.ApiException;
import com.faq.common.Repositories.UserRepository;
import com.faq.common.Requests.AccountRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    public Map<String, String> createAccount(AccountRequest accountRequest)
            throws ApiException {

        accountRequest.validate();
        verifyEmailNotInUse(accountRequest.getEmail());

        String hashedPassword =
                passwordEncoder.encode(accountRequest.getPassword());

        AccountEntity accountEntity =
                new AccountEntity(accountRequest.getEmail(), hashedPassword);

        this.userRepository.save(accountEntity);

        String token = jwtTokenUtil.generateToken(accountEntity);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        logger.info("Account has been created for {}",
                accountRequest.getEmail());

        return response;
    }

    public Map<String, String> loginAccount(AccountRequest accountRequest)
            throws ApiException {

        accountRequest.validate();

        AccountEntity accountEntity =
                new AccountEntity(accountRequest);

        verifyAccountExists(accountEntity);

        Map<String, String> response = new HashMap<>();

        String token = jwtTokenUtil.generateToken(accountEntity);

        response.put("token", token);

        logger.info("Account successfully logged in for {}",
                accountRequest.getEmail());

        return response;
    }


    private void verifyEmailNotInUse(String email) {

        boolean emailInUse = userRepository.existsByEmail(email);

        if (emailInUse) {
            throw new ApiException
                    (ApiException.ApiErrorType.ACCOUNT_EMAIL_IN_USE,
                            AccountService.class);
        }
    }

    private void verifyAccountExists(AccountEntity account) {

        Optional<AccountEntity> queryResult =
                userRepository.findByEmail(account.getEmail());

        boolean passwordsMatch = queryResult
                .map(databaseAccount ->
                        passwordEncoder.matches(account.getPassword(), databaseAccount.getPassword()))
                .orElseThrow(() ->
                        new ApiException(ApiException.ApiErrorType.ACCOUNT_DOES_NOT_EXIST, AccountService.class));

        if (!passwordsMatch) {
            throw new ApiException
                    (ApiException.ApiErrorType.ACCOUNT_PASSWORD_INVALIID,
                            AccountService.class);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new ApiException(ApiException.ApiErrorType.ACCOUNT_DOES_NOT_EXIST, AccountService.class));
    }
}
