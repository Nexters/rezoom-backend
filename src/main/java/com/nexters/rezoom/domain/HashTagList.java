package com.nexters.rezoom.domain;

//사용자가 입력한 모든 해시태그를 불러오기 위한 클래스
public class HashTagList {
    private String hashtagId;
    private String hashtagKeyword;

    public HashTagList(String hashtagId, String hashtagKeyword) {
        this.hashtagId = hashtagId;
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
