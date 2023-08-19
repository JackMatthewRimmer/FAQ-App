package com.faq.Controllers;

import com.faq.Services.AccountService;
import com.faq.common.Requests.AccountRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> createAccount(@RequestBody AccountRequest createAccountRequest) {

        Map<String, String> tokenResponse = accountService.createAccount(createAccountRequest);
        return tokenResponse;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> loginAccount(@RequestBody AccountRequest loginAccountRequest) {

        Map<String, String> tokenResponse = accountService.loginAccount(loginAccountRequest);
        return tokenResponse;
    }
}
