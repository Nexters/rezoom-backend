package com.nexters.rezoom.repository;

import com.nexters.rezoom.domain.ApplicationUser;

/**
 * Created by JaeeonJin on 2018-08-02.
 */
public interface ApplicationUserRepository {
    ApplicationUser selectOneByUsernameOnSecurity(String username);
    void insertOne(ApplicationUser user);
}
