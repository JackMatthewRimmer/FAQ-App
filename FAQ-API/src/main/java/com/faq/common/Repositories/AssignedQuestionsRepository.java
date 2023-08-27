package com.faq.common.Repositories;

import com.faq.common.Entities.AssignedQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignedQuestionsRepository extends JpaRepository<AssignedQuestionEntity, Long> {
}
