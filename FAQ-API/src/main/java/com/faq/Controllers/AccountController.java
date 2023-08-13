package com.faq.Controllers;

import com.faq.common.Requests.CreateAccountRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @GetMapping("/")
    public String index() {
        return "All users!";
    }

    @PostMapping("/")
    public String createAccount(@RequestBody CreateAccountRequest createAccountRequest) {
        return createAccountRequest.getEmail() + "\n" + createAccountRequest.getPassword();
    };
}
