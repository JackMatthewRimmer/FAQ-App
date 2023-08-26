package com.faq.common.Requests;

import com.faq.common.Exceptions.ApiException;

import static com.faq.common.Exceptions.ApiException.ApiErrorType.QUESTION_MISSING_CONTENT;
import static com.faq.common.Exceptions.ApiException.ApiErrorType.QUESTION_MISSING_TITLE;

public class QuestionRequest implements Request {

    private String title;

    private String content;

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

    @Override
    public boolean validate() {
        if (title.isBlank()) {
            throw new ApiException(QUESTION_MISSING_TITLE, QuestionRequest.class);
        }

        if (content.isBlank()) {
            throw new ApiException(QUESTION_MISSING_CONTENT, QuestionRequest.class);
        }
        return true;
    }
}
