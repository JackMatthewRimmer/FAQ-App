package com.faq.Services;

import com.faq.common.Exceptions.ApiException;
import com.faq.common.Repositories.UserRepository;
import com.faq.common.Requests.AccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    public String createAccount(AccountRequest accountRequest) throws ApiException {

        accountRequest.validate();

        this.userRepository.save(accountRequest);
        // TODO this should return JWT instead

        return accountRequest.getEmail() + '\n' + accountRequest.getPassword();
    }
}
