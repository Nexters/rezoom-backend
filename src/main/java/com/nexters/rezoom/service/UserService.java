package com.nexters.rezoom.service;

import com.nexters.rezoom.domain.ApplicationUser;
import com.nexters.rezoom.exception.DuplicateEmailException;
import com.nexters.rezoom.exception.WrongPasswordException;
import com.nexters.rezoom.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by JaeeonJin on 2018-08-22.
 */
@Service
public class UserService {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 이메일 중복체크 후 회원가입
     */
    public void signIn(ApplicationUser user) {
        String userEmail = applicationUserRepository.selectEmail(user.getUsername());

        if (userEmail == null)  applicationUserRepository.insertOne(user);
        else throw new DuplicateEmailException();
    }

    /**
     * 회원 정보 수정
     * 비밀번호 검증 후 데이터 수정
     */
    public void updateUserInfo(ApplicationUser user, String username) {
        // 새로운 비밀번호 암호화
        user.setNewPassword(bCryptPasswordEncoder.encode(user.getNewPassword()));

        // 기존 비밀번호 검증
        String password = applicationUserRepository.getPassword(username);
        boolean isRightPassword = bCryptPasswordEncoder.matches(user.getPassword(), password);

        // 수정 (비밀번호 검증 실패시, 예외처리)
        if (isRightPassword) applicationUserRepository.updateOne(user, username);
        else throw new WrongPasswordException();
    }
}
