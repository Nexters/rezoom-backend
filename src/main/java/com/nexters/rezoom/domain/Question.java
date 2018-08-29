package com.nexters.rezoom.domain;

import java.util.List;

public class Question {
    private int questionId;
    private String title;
    private String content;
    private List<HashTag> hashTags;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
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

    public List<HashTag> getHashTags() {
        return hashTags;
    }

    public void setHashTags(List<HashTag> hashTags) {
        this.hashTags = hashTags;
    }
}
