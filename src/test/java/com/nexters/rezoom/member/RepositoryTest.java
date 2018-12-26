package com.nexters.rezoom.member;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RepositoryTest {

    @Autowired
    private MemberRepository repository;

    @Test
    public void 멤버_정보_조회_성공() {
        // given
        Member member = new Member(UUID.randomUUID().toString(), "test", "test");
        repository.save(member);

        // when
        Member findMember = repository.findById(member.getId());

        // then
        assertEquals(member, findMember);
    }

    @Test
    public void 멤버_정보_조회_실패_없는아이디면_NULL() {
        // given
        String id = UUID.randomUUID().toString();

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
