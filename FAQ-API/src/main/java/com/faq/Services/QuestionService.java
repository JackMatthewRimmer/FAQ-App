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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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

    private Long assignQuestionToAccount(@NonNull QuestionEntity questionEntity,
                                         @NonNull AccountEntity principal) {

        AssignedQuestionEntity assignedQuestionEntity =
                new AssignedQuestionEntity(questionEntity, principal);

        assignedQuestionsRepository.save(assignedQuestionEntity);

        return assignedQuestionEntity.getAssignedQuestionsId();
    }
}
