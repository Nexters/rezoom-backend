package com.nexters.rezoom.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.nexters.rezoom.domain.ApplicationUser;
import com.nexters.rezoom.domain.view.UserView;
import com.nexters.rezoom.repository.ApplicationUserRepository;
import com.nexters.rezoom.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
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
    @JsonView(UserView.SignUp.class)
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody ApplicationUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userService.signIn(user);
    }

    @ApiOperation(value="로그인한 유저의 아이디(username)을 얻는다.")
    @GetMapping("/username")
    @ResponseStatus(HttpStatus.OK)
    public String getUsername(Principal principal) {
        return principal.getName();
    }

    @ApiOperation(value="로그인한 유저의 닉네임(name)을 얻는다.")
    @GetMapping("/name")
    @ResponseStatus(HttpStatus.OK)
    public String getName(Principal principal) {
        return applicationUserRepository.selectName(principal.getName());
    }

    @ApiOperation(value = "회원 정보 수정")
    @PutMapping("")
    @JsonView(UserView.UpdateInfo.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUserInfo(@RequestBody ApplicationUser user, Principal principal) {
        userService.updateUserInfo(user, principal.getName());
    }

    // TODO : 회원탈퇴 API 필요
}
