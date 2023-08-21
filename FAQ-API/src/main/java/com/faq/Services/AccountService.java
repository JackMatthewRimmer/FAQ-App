package com.faq.Services;

import com.faq.common.Entities.AccountEntity;
import com.faq.common.Exceptions.ApiException;
import com.faq.common.Repositories.UserRepository;
import com.faq.common.Requests.AccountRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    public Map<String, String> createAccount(AccountRequest accountRequest)
            throws ApiException {

        accountRequest.validate();
        AccountEntity accountEntity = new AccountEntity(accountRequest);
        verifyEmailNotInUse(accountRequest.getEmail());

        this.userRepository.save(accountEntity);

        Map<String, String> response = new HashMap<>();
        response.put("token", "this should be a real token");

        logger.info("Account has been created for {}",
                accountRequest.getEmail());
        return response;
    }

    public Map<String, String> loginAccount(AccountRequest accountRequest)
            throws ApiException {

        accountRequest.validate();
        AccountEntity accountEntity = new AccountEntity(accountRequest);
        verifyAccountExists(accountEntity);

        Map<String, String> response = new HashMap<>();
        response.put("token", "this should be a real token");

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
        boolean accountExists =
                userRepository.existsByEmailAndPassword
                        (account.getEmail(),
                                account.getPassword());
        if (!accountExists) {
            throw new ApiException
                    (ApiException.ApiErrorType.ACCOUNT_DOES_NOT_EXIST,
                            AccountService.class);
        }
    }
}
