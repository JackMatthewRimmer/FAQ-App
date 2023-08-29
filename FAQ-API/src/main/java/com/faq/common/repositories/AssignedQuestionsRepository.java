package com.faq.common.repositories;

import com.faq.common.entities.AssignedQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AssignedQuestionsRepository extends JpaRepository<AssignedQuestionEntity, Long> {

    @Query("SELECT aq.questionEntity.questionsId FROM AssignedQuestionEntity aq WHERE aq.accountEntity.accountsId = ?1")
    List<Long> findQuestionIdsByAccountId(Long accountId);

    Optional<AssignedQuestionEntity> findByAccountEntity_AccountsIdAndQuestionEntity_QuestionsId(long accountsId, long questionsId);
}
