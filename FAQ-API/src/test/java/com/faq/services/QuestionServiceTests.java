package com.faq.services;

import com.faq.common.entities.AccountEntity;
import com.faq.common.repositories.AssignedQuestionsRepository;
import com.faq.common.repositories.QuestionRepository;
import com.faq.common.requests.QuestionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTests {

    private static final String VALID_TITLE = "test title";

    private static final String VALID_CONTENT = "test content \nthis is the main part";

    private QuestionRequest questionRequest;

    private AccountEntity principal;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AssignedQuestionsRepository assignedQuestionsRepository;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    void initRequest() {
        this.questionRequest = new QuestionRequest();
        this.principal = new AccountEntity("test@domain.com", "password");
    }

    @Test
    void createQuestionWithValidRequestTest() {
        questionRequest.setTitle(VALID_TITLE);
        questionRequest.setContent(VALID_CONTENT);

        Map<String, String> response = questionService.createQuestion(questionRequest, principal);
        assertEquals("/questions/0", response.get("Location"));
    }
}
