package com.nexters.rezoom.controller;

import com.nexters.rezoom.domain.ApplicationUser;
import com.nexters.rezoom.exception.DuplicateEmailException;
import com.nexters.rezoom.repository.ApplicationUserRepository;
import com.nexters.rezoom.service.UserService;
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

    private UserService userService;
    private ApplicationUserRepository applicationUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @ApiOperation(value="회원가입")
    @PostMapping("/sign-up")
    public void signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.signIn(user);
    }

    @ApiOperation(value="로그인한 유저의 이름(username)을 얻는다.")
    @GetMapping
    public String getUsername(Principal principal) {
        return principal.getName();
    }
    
    @ApiOperation(value="회원 정보 수정에서 현재 비밀번호를 확인한다.")
    @PostMapping("/confirm/password")
    public boolean confirmPassword(@RequestBody ApplicationUser user, Principal principal) {
    	String password = applicationUserRepository.confirmPassword(principal.getName());
    	return bCryptPasswordEncoder.matches(user.getPassword(), password);
    }

}
