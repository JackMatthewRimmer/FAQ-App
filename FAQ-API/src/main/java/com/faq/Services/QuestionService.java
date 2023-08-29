package com.faq.Services;

import com.faq.common.Entities.AccountEntity;
import com.faq.common.Entities.AssignedQuestionEntity;
import com.faq.common.Entities.QuestionEntity;
import com.faq.common.Exceptions.ApiException;
import com.faq.common.Repositories.AssignedQuestionsRepository;
import com.faq.common.Repositories.QuestionRepository;
import com.faq.common.Requests.QuestionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.faq.common.Exceptions.ApiException.ApiErrorType.QUESTION_DOES_NOT_EXISTS;
import static com.faq.common.Exceptions.ApiException.ApiErrorType.QUESTION_NOT_AUTHORIZED;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AssignedQuestionsRepository assignedQuestionsRepository;

    public Map<String, String> createQuestion(@NonNull QuestionRequest questionRequest,
                                              @NonNull AccountEntity principal) throws ApiException {
        questionRequest.validate();

        QuestionEntity questionEntity = new QuestionEntity(questionRequest);

        questionRepository.save(questionEntity);

        Long assignedQuestionId = assignQuestionToAccount(questionEntity, principal);

        Map<String, String> response = new HashMap<>();
        response.put("Location", "/questions/" + assignedQuestionId.toString());
        return response;
    }

    public List<QuestionEntity> getQuestions(@NonNull AccountEntity principal) throws ApiException {
        List<Long> questionIds = assignedQuestionsRepository.findQuestionIdsByAccountId(principal.getAccountsId());
        return questionRepository.findAllById(questionIds);
    }

    public void updateQuestion(@NonNull AccountEntity principal, Long questionId, QuestionRequest questionRequest)
            throws ApiException {

        questionRequest.validate();
        QuestionEntity questionEntity = verifyAccountOwnsQuestion(principal, questionId);
        questionEntity.setTitle(questionRequest.getTitle());
        questionEntity.setContent(questionRequest.getContent());
        questionRepository.save(questionEntity);
    }

    private QuestionEntity verifyAccountOwnsQuestion(@NonNull AccountEntity principal, Long questionId)
            throws ApiException {

        AssignedQuestionEntity assignedQuestionEntity = assignedQuestionsRepository.
                findByAccountEntity_AccountsIdAndQuestionEntity_QuestionsId(principal.getAccountsId(), questionId)
                .orElseThrow(() -> new ApiException(QUESTION_NOT_AUTHORIZED, QuestionService.class));

        return assignedQuestionEntity.getQuestionEntity();
    }

    private Long assignQuestionToAccount(@NonNull QuestionEntity questionEntity,
                                         @NonNull AccountEntity principal) {

        AssignedQuestionEntity assignedQuestionEntity =
                new AssignedQuestionEntity(questionEntity, principal);

        assignedQuestionsRepository.save(assignedQuestionEntity);

        return assignedQuestionEntity.getAssignedQuestionsId(); }
}
