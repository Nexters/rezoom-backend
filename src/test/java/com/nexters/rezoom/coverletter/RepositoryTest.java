package com.nexters.rezoom.coverletter;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.infra.JpaCoverletterRepository;
import com.nexters.rezoom.member.domain.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;


@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RepositoryTest {

    @Autowired
    private JpaCoverletterRepository repository;

    private Member member;

    public RepositoryTest() {
        member = new Member("admin@admin.admin", "", "");
    }

    @Test
    public void 자기소개서_저장() {
        Coverletter coverletter = new Coverletter(member, "저장테스트");
        repository.save(coverletter);

        assertTrue(coverletter.getId() > 0);
    }

    @Test
    public void 자기소개서_단건_조회() {
        // given
        long coverletterId = 15L;

        // when
        Coverletter coverletter = repository.findById(member, coverletterId);

        // then
        assertEquals(coverletter.getId(), coverletterId);
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

        // when
        List<Coverletter> coverletters = repository.findAll(member, 0, 100);

        // then
    }

    @Test
    public void 자기소개서_리스트_조회_없으면_EMTPY() {
        // given
        Member memberWhohasNoCoverletter = new Member("wlswodjs_@Naver.com", "", "");

        // when
        List<Coverletter> coverletters = repository.findAll(memberWhohasNoCoverletter, 0, 100);

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
