package com.nexters.rezoom.domain;

import java.util.Arrays;
import java.util.List;

public class Question {

    private int resumeId;
    private int userId;
    private int questionId;
    private String title;
    private String content;
    List<String> hashTagList;

    public List<String> getHashTagList() {
        return hashTagList;
    }

    public void setHashTagList(String hashTagStr) {

        this.hashTagList = Arrays.asList(hashTagStr.split(","));
    }

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

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
}
