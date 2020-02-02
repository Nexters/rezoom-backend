package com.nexters.rezoom.core.domain.member;

import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.domain.member.application.RezoomMemberService;
import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.core.domain.member.domain.MemberRepository;
import com.nexters.rezoom.util.TestObjectUtils;
import com.nexters.rezoom.core.domain.member.api.dto.MemberDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

/**
 * Created by momentjin@gmail.com on 2019-07-24
 * Github : http://github.com/momentjin
 */

@SpringBootTest(classes = {RezoomMemberService.class})
@ExtendWith(SpringExtension.class)
public class RezoomMemberServiceTest {

    @Autowired
    private RezoomMemberService service;

    @MockBean
    private MemberRepository memberRepository;

    @MockBean
    private PasswordEncoder encoder;

    @Test
    public void 회원가입_성공() {
        // given
        MemberDto.SignUpReq req = TestObjectUtils.createMemberSignReqDto();

        given(memberRepository.findById(anyString()))
                .willReturn(Optional.empty());

        // when
        service.signUp(req);

        // then
        verify(memberRepository, atLeastOnce()).save(any(Member.class));
    }

    @Test()
    public void 이메일중복이면_회원가입_실패() {
        // given
        MemberDto.SignUpReq req = TestObjectUtils.createMemberSignReqDto();

        given(memberRepository.findById(anyString()))
                .willReturn(Optional.of(new Member("", "", "")));

        // when & then (동일한 ID로 재가입해서 중복 테스트 수행)
        assertThrows(BusinessException.class, () -> {
            service.signUp(req);
        });
    }

    @Test
    public void 멤버_조회_성공() {
        // given
        given(memberRepository.findById(anyString()))
                .willReturn(Optional.of(new Member("test", "test", "test")));
        // when
        MemberDto.ViewRes memberInfo = service.getMemberInfo("test");

        // then
        assertEquals(memberInfo.getId(), "test");
        assertEquals(memberInfo.getName(), "test");
    }

    @Test
    public void 멤버_조회_실패하면_BusinessException() {
        // given
        given(memberRepository.findById(anyString()))
                .willReturn(Optional.empty());

        // when & then
        assertThrows(BusinessException.class, () -> {
            service.getMemberInfo(anyString());
        });
    }


}
