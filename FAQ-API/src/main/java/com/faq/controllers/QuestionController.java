package com.faq.controllers;

import com.faq.services.QuestionService;
import com.faq.common.entities.AccountEntity;
import com.faq.common.requests.QuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

        AccountEntity principal = getPrincipal();
        return new ResponseEntity<>
                (questionService.handleCreateQuestion(questionRequest, principal), HttpStatus.CREATED);
    }

    @GetMapping(value="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getQuestions
            (@RequestParam(required = false) Integer pageNumber,
             @RequestParam(required = false, defaultValue = "10") Integer pageSize) {

        AccountEntity principal = getPrincipal();

        if (pageNumber == null) {
            return new ResponseEntity<>
                    (questionService.handleGetQuestions(principal), HttpStatus.OK);
        }
        else {
            Pageable pageInfo = PageRequest.of(pageNumber, pageSize);
            return new ResponseEntity<>
                    (questionService.handleGetQuestions(principal, pageInfo), HttpStatus.OK);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateQuestion
            (@PathVariable Long id, @RequestBody QuestionRequest questionRequest) {

        AccountEntity principal = getPrincipal();
        questionService.handleUpdateQuestion(principal, id, questionRequest);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    private AccountEntity getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (AccountEntity) authentication.getPrincipal();
    }
}
