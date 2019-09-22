package com.nexters.rezoom.member;

import com.nexters.rezoom.config.exception.EntityNotFoundException;
import com.nexters.rezoom.config.exception.InvalidValueException;
import com.nexters.rezoom.member.application.MemberService;
import com.nexters.rezoom.member.dto.MemberDto;
import com.nexters.rezoom.util.TestObjectUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by momentjin@gmail.com on 2019-07-24
 * Github : http://github.com/momentjin
 */

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MemberServiceTest {


    @Autowired
    private MemberService service;

    @Test
    @DisplayName("회원가입")
    public void memberCreateTest1() {
        // given
        MemberDto.SignUpReq req = TestObjectUtils.createMemberSignReqDto();

        // when
        service.signUp(req);

        // then
        MemberDto.ViewRes findMember = service.getMemberInfo(req.getId());
        assertEquals(findMember.getId(), req.getId());
        assertEquals(findMember.getName(), req.getName());
    }

    @Test()
    @DisplayName("ID가 중복이면 InvalidValueException 예외 발생")
    public void memberCreateTest2() {
        // given
        MemberDto.SignUpReq req = TestObjectUtils.createMemberSignReqDto();
        service.signUp(req);

        // when & then (동일한 ID로 재가입해서 중복 테스트 수행)
        assertThrows(InvalidValueException.class, () -> {
            service.signUp(req);
        });
    }

    @Test
    @DisplayName("멤버 조회")
    public void memberSelectTest1() {
        // given
        MemberDto.SignUpReq req = TestObjectUtils.createMemberSignReqDto();
        service.signUp(req);

        // when
        MemberDto.ViewRes memberInfo = service.getMemberInfo(req.getId());

        // then
        assertEquals(memberInfo.getId(), req.getId());
        assertEquals(memberInfo.getName(), req.getName());
    }

    @Test
    @DisplayName("없는 멤버 조회시 EntityNotFoundException 발생")
    public void memberSelectTest2() {
        // given
        String randomId = UUID.randomUUID().toString();

        assertThrows(EntityNotFoundException.class, () -> {
            service.getMemberInfo(randomId);
        });
    }


}
