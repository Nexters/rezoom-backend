package com.nexters.rezoom.member;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RepositoryTest {

    @Autowired
    private MemberRepository repository;

    @Test
    public void 멤버_정보_조회_성공() {
        Member findMember = repository.findById("admin@admin.admin");
        assertFalse(findMember.getId().isEmpty());
    }

    @Test
    public void 멤버_정보_조회_실패_없는아이디면_NULL() {
        // given
        String id = "admin@admin.admin123";

        // when
        Member findMember = repository.findById(id);

        // then
        assertNull(findMember);
    }

    @Test
    public void 멤버_저장() {
        // given
        Member member = new Member("testMember", "testName", "testPw");

        // when
        repository.save(member);

        // then
        Member findMember = repository.findById(member.getId());
        assertEquals(member, findMember);
    }

    // TODO : jpa에서 pk 오류 잡는 방법 알아야 함
//    // @Test(expected = PersistenceException.class)
//    public void 멤버_저장_실패_아이디중복() {
//        // given
//        Member member = new Member("admin@admin.admin", "testName", "testPw");
//
//        // when
//        repository.save(member);
//
//        // then
//        // expected PersistenExceiption
//    }


}
