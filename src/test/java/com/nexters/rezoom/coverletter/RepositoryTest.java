package com.nexters.rezoom.coverletter;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.infra.JpaCoverletterRepository;
import com.nexters.rezoom.dto.MemberDto;
import com.nexters.rezoom.member.application.MemberService;
import com.nexters.rezoom.member.domain.Member;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;


@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RepositoryTest {

    @Autowired
    private JpaCoverletterRepository repository;

    @Autowired
    private MemberService memberService;
    private Member member;

    @Before
    public void createMember() {
        member = new Member(UUID.randomUUID().toString(), "test", "test");
        memberService.signUp(new MemberDto.SignUpReq(member.getId(), member.getPassword(), member.getName()));
    }

    @Test
    public void 자기소개서_저장() {
        // given
        Coverletter coverletter = new Coverletter(member, "저장테스트");

        // when
        repository.save(coverletter);

        // then
        assertTrue(coverletter.getId() > 0);
        assertEquals(coverletter.getMember(), member);
    }

    @Test
    public void 자기소개서_단건_조회() {
        // given
        Coverletter coverletter = new Coverletter(member, "저장테스트");
        repository.save(coverletter);

        // when
        Coverletter findCoverletter = repository.findById(member, coverletter.getId());

        // then
        assertEquals(coverletter.getId(), findCoverletter.getId());
        assertEquals(coverletter.getMember(), member);
    }

    @Test
    public void 자기소개서_단건_조회_없으면_NULL() {
        // given
        long coverletterId = 199995L;

        // when
        Coverletter coverletter = repository.findById(member, coverletterId);

        // then
        assertNull(coverletter);
    }

    @Test
    public void 자기소개서_리스트_조회() {
        // given
        Coverletter coverletter1 = new Coverletter(member, "저장테스트1");
        Coverletter coverletter2 = new Coverletter(member, "저장테스트2");
        repository.save(coverletter1);
        repository.save(coverletter2);

        // when
        List<Coverletter> coverletters = repository.findAll(member, 0, 10);

        // then
        assertEquals(coverletters.get(0), coverletter1);
        assertEquals(coverletters.get(1), coverletter2);
    }

    @Test
    public void 자기소개서_리스트_조회_없으면_EMTPY() {
        // given
        Member member = new Member(UUID.randomUUID().toString(), "test", "test");
        memberService.signUp(new MemberDto.SignUpReq(member.getId(), member.getPassword(), member.getName()));

        // when
        List<Coverletter> coverletters = repository.findAll(member, 0, 100);

        // then
        assertTrue(coverletters.isEmpty());
    }

    @Test
    public void 자기소개서_삭제() {
        // given
        Coverletter coverletter = new Coverletter(member, "저장테스트");
        repository.save(coverletter);

        // when
        repository.delete(coverletter);

        // then
        Coverletter findCoverletter = repository.findById(member, coverletter.getId());
        assertNull(findCoverletter);
    }
}
