package com.faq.Controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {


    @GetMapping(value="/")
    public Map<String, String> test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Map<String, String> dummyResponse = new HashMap<>();
        dummyResponse.put("details", userDetails.getUsername());
        return dummyResponse;
    }

}
