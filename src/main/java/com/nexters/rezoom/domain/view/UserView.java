package com.nexters.rezoom.domain.view;

public class UserView {

    // username, name, password
    public interface SignUp {}

    // name, password, new password
    public interface UpdateInfo extends SignUp {}

}
