package com.faq.Controllers;

import com.faq.common.Requests.AccountRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class AuthenticationController {


    @GetMapping(value="/")
    public String test() {
        return "HELLO WORLD";
    }

}
