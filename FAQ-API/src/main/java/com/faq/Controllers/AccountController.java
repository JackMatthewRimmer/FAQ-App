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

import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createAccount(@RequestBody AccountRequest createAccountRequest) {
        return new ResponseEntity<>
                (accountService.createAccount(createAccountRequest), HttpStatus.CREATED);
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> loginAccount
            (@RequestBody AccountRequest loginAccountRequest) {

        return new ResponseEntity<>
                (accountService.loginAccount(loginAccountRequest), HttpStatus.OK);
    }
}
