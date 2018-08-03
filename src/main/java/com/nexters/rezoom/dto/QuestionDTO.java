package com.nexters.rezoom.dto;

import com.nexters.rezoom.domain.HashTag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDTO {
    private int questionId;
    private String title;
    private String content;
    private List<HashTag> hashTags;

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
