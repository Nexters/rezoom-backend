package com.nexters.rezoom.domain;

/**
 * Created by JaeeonJin on 2018-07-17.
 */
public class ApplicationUser {
    private String username; // id, email

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name; // nickname
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
