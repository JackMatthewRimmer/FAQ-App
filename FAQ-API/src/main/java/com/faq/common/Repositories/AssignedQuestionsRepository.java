package com.faq.common.Repositories;

import com.faq.common.Entities.AssignedQuestionEntity;
import com.faq.common.Entities.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssignedQuestionsRepository extends JpaRepository<AssignedQuestionEntity, Long> {

    @Query("SELECT aq.questionEntity.questionsId FROM AssignedQuestionEntity aq WHERE aq.accountEntity.accountsId = ?1")
    public List<Long> findQuestionIdsByAccountId(Long accountId);
}
