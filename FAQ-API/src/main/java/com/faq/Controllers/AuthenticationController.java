package com.faq.Controllers;

import com.faq.common.Requests.AccountRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @PostMapping("/")
    void authenticateUser(@RequestBody AccountRequest loginRequest) {
        
    }

}
