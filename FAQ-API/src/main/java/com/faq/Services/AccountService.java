package com.faq.Services;

import com.faq.common.Exceptions.ApiException;
import com.faq.common.Repositories.UserRepository;
import com.faq.common.Requests.AccountRequest;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountService {

    @Autowired
    private UserRepository userRepository;

    public Map<String, String> createAccount(AccountRequest accountRequest) throws ApiException {

        accountRequest.validate();

        verifyEmailNotInUse(accountRequest.getEmail());

        this.userRepository.save(accountRequest);
        // TODO this should return JWT instead

        Map<String, String> response = new HashMap<>();
        response.put("token", "this should be a real token");
        return response;
    }

    private void verifyEmailNotInUse(String email) {

        List<AccountRequest> accounts = userRepository.findByEmail(email);
        if (!accounts.isEmpty()) {
            throw new ApiException(ApiException.ApiErrorType.ACCOUNT_EMAIL_IN_USE);
        }

    }
}
