package com.faq.common.entities;

import com.faq.common.requests.QuestionRequest;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "questions")
public class QuestionEntity {

    @Id
    @Column(name = "questions_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long questionsId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    public QuestionEntity() {}

    public QuestionEntity(@NonNull QuestionRequest questionRequest) {
        this.title = questionRequest.getTitle();
        this.content = questionRequest.getContent();
    }

    public long getQuestionsId() {
        return questionsId;
    }

    public void setQuestionsId(long questionsId) {
        this.questionsId = questionsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
