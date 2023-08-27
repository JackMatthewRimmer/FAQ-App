package com.faq.common.Entities;

import javax.persistence.*;

@Entity
@Table(name = "assigned_questions")
public class AssignedQuestionEntity {

    @Id
    @Column(name = "assigned_questions_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long assignedQuestionsId;

    @ManyToOne
    @JoinColumn(name = "accounts_id")
    private AccountEntity accountEntity;

    @ManyToOne
    @JoinColumn(name = "questions_id")
    private QuestionEntity questionEntity;

    public AssignedQuestionEntity() {}

    public AssignedQuestionEntity(QuestionEntity questionEntity, AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
        this.questionEntity = questionEntity;
    }
}
