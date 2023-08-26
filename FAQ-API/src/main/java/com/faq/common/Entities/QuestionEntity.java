package com.faq.common.Entities;

import javax.persistence.*;

@Entity
@Table(name = "questions")
public class QuestionEntity {

    @Id
    @Column(name = "questions_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questions_id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private AccountEntity account;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    public QuestionEntity() {}
}
