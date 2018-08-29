package com.nexters.rezoom.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.nexters.rezoom.domain.view.UserView;

/**
 * Created by JaeeonJin on 2018-07-17.
 */
public class ApplicationUser {

    @JsonView(UserView.SignUp.class)
    private String username; // id (email)

    @JsonView(UserView.SignUp.class)
    private String name; // nickname

    @JsonView(UserView.SignUp.class)
    private String password;

    @JsonView(UserView.UpdateInfo.class)
    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
