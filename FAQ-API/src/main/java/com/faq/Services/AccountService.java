package com.faq.Services;

import com.faq.common.Exceptions.ApiException;
import com.faq.common.Requests.AccountRequest;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public String createAccount(AccountRequest accountRequest) throws ApiException {

        accountRequest.validate();

        // TODO this should return JWT instead

        return accountRequest.getEmail() + '\n' + accountRequest.getPassword();
    }
}
