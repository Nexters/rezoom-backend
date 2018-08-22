package com.nexters.rezoom.controller;

import com.nexters.rezoom.domain.ApplicationUser;
import com.nexters.rezoom.repository.ApplicationUserRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by JaeeonJin on 2018-07-21.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @ApiOperation(value="회원가입")
    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        applicationUserRepository.insertOne(user);
    }

    @ApiOperation(value="로그인한 유저의 이름(username)을 얻는다.")
    @GetMapping
    public String getUsername(Principal principal) {
        return principal.getName();
    }
    
    @ApiOperation(value="회원 정보 수정에서 현재 비밀번호를 확인한다.")
    @PostMapping("/passwords")
    public boolean confirmPassword(@RequestBody ApplicationUser user) {
    	String password = applicationUserRepository.confirmPassword(user.getUsername());
    	return bCryptPasswordEncoder.matches(user.getPassword(), password);
    }

}
