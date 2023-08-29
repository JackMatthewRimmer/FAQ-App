package com.faq.Controllers;

import com.faq.Services.QuestionService;
import com.faq.common.Entities.AccountEntity;
import com.faq.common.Requests.QuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/questions")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @PostMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createQuestion(@RequestBody QuestionRequest questionRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountEntity principal = (AccountEntity) authentication.getPrincipal();

        return new ResponseEntity<>
                (questionService.createQuestion(questionRequest, principal), HttpStatus.CREATED);
    }

    @GetMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getQuestions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountEntity principal = (AccountEntity) authentication.getPrincipal();

        return new ResponseEntity<>
                (questionService.getQuestions(principal), HttpStatus.OK);
    }

}
