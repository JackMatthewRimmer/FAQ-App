package com.faq.Controllers;

import com.faq.common.Requests.AccountRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @GetMapping("/")
    public String index() {
        System.out.println("get request");
        return "All users!";
    }

    @PostMapping("/")
    public String createAccount(@RequestBody AccountRequest createAccountRequest) {
        // TODO create the account in the database
        System.out.println("createAccountRequest = " + createAccountRequest);
        createAccountRequest.validate();
        return createAccountRequest.getEmail() + "\n" + createAccountRequest.getPassword();
    };
}
