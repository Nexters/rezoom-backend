package com.nexters.rezoom.service;

import com.nexters.rezoom.domain.ApplicationUser;
import com.nexters.rezoom.exception.DuplicateEmailException;
import com.nexters.rezoom.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by JaeeonJin on 2018-08-22.
 */
@Service
public class UserService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    /**
     * 이메일 중복체크 후 회원가입
     */
    public void signIn(ApplicationUser user) {
        String userEmail = applicationUserRepository.selectEmail(user.getUsername());

        if (userEmail == null)  applicationUserRepository.insertOne(user);
        else throw new DuplicateEmailException();
    }
}
