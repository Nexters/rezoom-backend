package com.nexters.rezoom.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.nexters.rezoom.domain.HashTag;
import com.nexters.rezoom.domain.Resume;
import com.nexters.rezoom.domain.view.QuestionView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Question {

    @JsonView(QuestionView.Simple.class)
    private int questionId;

    @JsonView(QuestionView.Simple.class)
    private String title;

    @JsonView(QuestionView.Simple.class)
    private String content;

    @JsonView(QuestionView.Simple.class)
    private List<HashTag> hashTags;

    @JsonView(QuestionView.Search.class)
    private Resume resume;

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public List<HashTag> getHashTags() {
        return hashTags;
    }

    public void setHashTags(String commaSeperatedHashtag) {
        String [] hashTags = commaSeperatedHashtag.split(",");
        this.hashTags = new ArrayList<>();
        for (String hashTagKeyword : hashTags) {
            this.hashTags.add(new HashTag(hashTagKeyword));
        }
    }

    public void setHashTags(List<HashTag> hashTags) {
        this.hashTags = hashTags;
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
