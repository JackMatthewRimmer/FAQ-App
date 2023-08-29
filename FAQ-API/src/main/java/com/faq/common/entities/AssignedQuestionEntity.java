package com.faq.common.entities;

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

    public long getAssignedQuestionsId() {
        return assignedQuestionsId;
    }

    public void setAssignedQuestionsId(long assignedQuestionsId) {
        this.assignedQuestionsId = assignedQuestionsId;
    }

    public AccountEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    public QuestionEntity getQuestionEntity() {
        return questionEntity;
    }

    public void setQuestionEntity(QuestionEntity questionEntity) {
        this.questionEntity = questionEntity;
    }
}
