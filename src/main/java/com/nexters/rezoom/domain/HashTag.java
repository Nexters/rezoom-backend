package com.nexters.rezoom.domain;

/**
 * Created by JaeeonJin on 2018-08-02.
 */
public class HashTag {
    private String hashtagId;
    private String hashtagKeyword;

    public HashTag(String hashtagKeyword) {
        this.hashtagKeyword = hashtagKeyword;
    }

    public String getHashtagId() {
        return hashtagId;
    }

    public void setHashtagId(String hashtagId) {
        this.hashtagId = hashtagId;
    }

    public String getHashtagKeyword() {
        return hashtagKeyword;
    }

    public void setHashtagKeyword(String hashtagKeyword) {
        this.hashtagKeyword = hashtagKeyword;
    }
}
