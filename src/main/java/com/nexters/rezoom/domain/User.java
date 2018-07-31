package com.nexters.rezoom.domain;

/**
 * Created by JaeeonJin on 2018-07-17.
 */
public class User {
    private int userId;
    private String userName;
    private String socialType;

    public User() {
    }

    public User(String userName, String socialType) {
        this.userName = userName;
        this.socialType = socialType;
    }

    public User(int userId, String userName, String socialType) {
        this.userId = userId;
        this.userName = userName;
        this.socialType = socialType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSocialType() {
        return socialType;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }
}
