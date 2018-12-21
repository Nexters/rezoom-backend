package com.nexters.rezoom.member;

import com.nexters.rezoom.dto.MemberDto;
import com.nexters.rezoom.member.application.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

    @Autowired
    private MemberService service;

    @Test
    public void 회원가입_성공() {
        // given
        MemberDto.SignUpReq req = new MemberDto.SignUpReq("testId", "testPw", "testNm");

        // then
        service.signUp(req);

        // when
        MemberDto.ViewRes findMemberInfo = service.getMemberInfo(req.getId());
        assertEquals(findMemberInfo.getId(), req.getId());
        assertEquals(findMemberInfo.getName(), req.getName());
    }

    @Test(expected = RuntimeException.class)
    public void 회원가입_실패_중복ID() {
        // given
        String id = "asdasdasdas";
        MemberDto.SignUpReq req = new MemberDto.SignUpReq(id, "testPw", "testNm");
        service.signUp(req);

        // when
        service.signUp(req);

        // then
        // expected RuntimeException
    }

    @Test
    public void 로그인_성공() {
        // given
        MemberDto.SignUpReq signUpReq = new MemberDto.SignUpReq("testId", "testPw", "testNm");
        service.signUp(signUpReq);

        MemberDto.SignInReq signInReq = new MemberDto.SignInReq("testId", "testPw");

        // when
        boolean isSuccess = service.signIn(signInReq);

        // then
        assertTrue(isSuccess);
    }

    @Test(expected = RuntimeException.class)
    public void 로그인_실패_없는아이디() {
        // given
        MemberDto.SignUpReq signUpReq = new MemberDto.SignUpReq("testId", "testPw", "testNm");
        service.signUp(signUpReq);

        MemberDto.SignInReq signInReq = new MemberDto.SignInReq("testId2", "testPw");

        // when
        boolean isSuccess = service.signIn(signInReq);

        // when
        // expected RuntimeException

    }

    @Test(expected = RuntimeException.class)
    public void 로그인_실패_비밀번호틀림() {
        // given
        MemberDto.SignUpReq signUpReq = new MemberDto.SignUpReq("testId", "testPw", "testNm");
        service.signUp(signUpReq);

        MemberDto.SignInReq signInReq = new MemberDto.SignInReq("testId", "testPw2");

        // when
        boolean isSuccess = service.signIn(signInReq);

        // when
        // expected RuntimeException
    }

}
