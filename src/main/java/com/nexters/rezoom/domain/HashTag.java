package com.nexters.rezoom.domain;

/**
 * Created by JaeeonJin on 2018-08-02.
 */
public class HashTag {
    private int hashtagId;
    private String hashtagKeyword;

    public HashTag(String hashtagKeyword) {
        this.hashtagKeyword = hashtagKeyword;
    }

    public HashTag(int hashtagId, String hashtagKeyword) {
        this.hashtagId = hashtagId;
        this.hashtagKeyword = hashtagKeyword;
    }

    public int getHashtagId() {
        return hashtagId;
    }

    public void setHashtagId(int hashtagId) {
        this.hashtagId = hashtagId;
    }

    public String getHashtagKeyword() {
        return hashtagKeyword;
    }

    public void setHashtagKeyword(String hashtagKeyword) {
        this.hashtagKeyword = hashtagKeyword;
    }
}
