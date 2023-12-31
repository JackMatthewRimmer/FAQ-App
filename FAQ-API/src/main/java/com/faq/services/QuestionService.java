package com.faq.services;

import com.faq.common.entities.AccountEntity;
import com.faq.common.entities.AssignedQuestionEntity;
import com.faq.common.entities.QuestionEntity;
import com.faq.common.exceptions.ApiException;
import com.faq.common.repositories.AssignedQuestionsRepository;
import com.faq.common.repositories.QuestionRepository;
import com.faq.common.requests.QuestionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.faq.common.exceptions.ApiException.ApiErrorType.QUESTION_DOES_NOT_EXISTS;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AssignedQuestionsRepository assignedQuestionsRepository;

    Logger logger = LoggerFactory.getLogger(QuestionService.class);

    public Map<String, String> handleCreateQuestion
            (@NonNull QuestionRequest questionRequest, @NonNull AccountEntity principal)
            throws ApiException {

        questionRequest.validate();
        QuestionEntity questionEntity = new QuestionEntity(questionRequest);
        questionRepository.save(questionEntity);
        Long assignedQuestionId = assignQuestionToAccount(questionEntity, principal);

        logger.info("Question successfully created with id {}", questionEntity.getQuestionsId());

        Map<String, String> response = new HashMap<>();
        response.put("Location", "/questions/" + assignedQuestionId.toString());
        return response;
    }

    public Map<String, Object> handleGetQuestions
            (@NonNull AccountEntity principal, Pageable pageInfo) throws ApiException {

        Page<QuestionEntity> page = getPaginatedQuestions(principal, pageInfo);
        Map<String, Object> response = parsePageToResponse("questions", page);
        return response;

    }

    public Map<String, QuestionEntity> handleGetQuestions
            (@NonNull AccountEntity principal) throws ApiException {

        List<QuestionEntity> questions = getAllQuestions(principal);
        return parseListToResponse("questions", questions);
    }

    public void handleUpdateQuestion
            (@NonNull AccountEntity principal, Long questionId, QuestionRequest questionRequest)
            throws ApiException {

        questionRequest.validate();
        QuestionEntity questionEntity = verifyQuestionsExistsForAccount(principal, questionId);
        questionEntity.setTitle(questionRequest.getTitle());
        questionEntity.setContent(questionRequest.getContent());
        questionRepository.save(questionEntity);
        logger.info("Question successfully update with id {}", questionEntity.getQuestionsId());
    }

    private Page<QuestionEntity> getPaginatedQuestions
            (@NonNull AccountEntity principal, Pageable pageInfo) throws ApiException {

        List<Long> questionIds = assignedQuestionsRepository
                .findQuestionIdsByAccountId(principal.getAccountsId());
        logger.info("Questions fetched for {}", principal.getEmail());
        return questionRepository.findAllByQuestionsIdIn(questionIds, pageInfo);
    }

    private List<QuestionEntity> getAllQuestions
            (@NonNull AccountEntity principal) throws ApiException {

        List<Long> questionIds = assignedQuestionsRepository
                .findQuestionIdsByAccountId(principal.getAccountsId());
        logger.info("Questions fetched for {}", principal.getEmail());
        return questionRepository.findAllById(questionIds);
    }

    private QuestionEntity verifyQuestionsExistsForAccount
            (@NonNull AccountEntity principal, Long questionId)
            throws ApiException {

        AssignedQuestionEntity assignedQuestionEntity = assignedQuestionsRepository.
                findByAccountEntity_AccountsIdAndQuestionEntity_QuestionsId
                        (principal.getAccountsId(), questionId)
                .orElseThrow(() -> new
                        ApiException(QUESTION_DOES_NOT_EXISTS, QuestionService.class));

        return assignedQuestionEntity.getQuestionEntity();
    }

    private Long assignQuestionToAccount
            (@NonNull QuestionEntity questionEntity, @NonNull AccountEntity principal) {

        AssignedQuestionEntity assignedQuestionEntity =
                new AssignedQuestionEntity(questionEntity, principal);

        assignedQuestionsRepository.save(assignedQuestionEntity);

        return assignedQuestionEntity.getAssignedQuestionsId();
    }

    private Map<String, Object> parsePageToResponse(String contentName, Page<QuestionEntity> page) {
        Map<String, Object> response = new HashMap<>();
        response.put(contentName, page.getContent());
        response.put("currentPage", page.getNumber());
        response.put("totalItems", page.getTotalElements());
        response.put("totalPages", page.getTotalPages());
        return response;
    }

    private <T> Map<String, T> parseListToResponse(String contentName, List<T> items) {
        Map<String, T> response = new HashMap<>();
        response.put(contentName, (T) items);
        return response;
    }

}
