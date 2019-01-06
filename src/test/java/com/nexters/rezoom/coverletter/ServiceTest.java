package com.nexters.rezoom.coverletter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.rezoom.config.common.Paging;
import com.nexters.rezoom.coverletter.application.CoverletterService;
import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.dto.CoverletterDto;
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

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

    @Autowired
    private CoverletterService service;

    @Autowired
    private CoverletterRepository repository;

    @Autowired
    private MemberService memberService;
    private Member member;

    @Before
    public void createMember() {
        member = new Member(UUID.randomUUID().toString(), "test", "test");
        memberService.signUp(new MemberDto.SignUpReq(member.getId(), member.getPassword(), member.getName()));
    }

    @Test
    public void 자기소개서_단건조회() {
        // given
        Coverletter coverletter = new Coverletter(member, "testCompany");
        repository.save(coverletter);

        long coverletterId = coverletter.getId();

        // when
        CoverletterDto.ViewRes res = service.getView(member, coverletterId);

        // then
        assertEquals(res.getId(), coverletterId);
        assertEquals(res.getCompanyName(), coverletter.getCompanyName());
        assertEquals(res.getQuestions().size(), 0);
    }

    @Test(expected = RuntimeException.class)
    public void 자기소개서_단건조회_없으면_RuntimeException() {
        // given
        long coverletterId = 121313L;

        // when
        service.getView(member, coverletterId);

        // then
        // expected RuntimeException
    }

    @Test
    public void 자기소개서_리스트_조회() {
        // given
        Coverletter coverletter1 = new Coverletter(member, "testCompany");
        Coverletter coverletter2 = new Coverletter(member, "testCompany");
        repository.save(coverletter1);
        repository.save(coverletter2);

        int pageNo = 1;

        // when
        CoverletterDto.ListRes res = service.getList(member, new Paging(pageNo));

        // then
        assertEquals(res.getCoverletters().size(), 2);
    }

    @Test
    public void 자기소개서_리스트_조회_빈값이면_NULL이아니라_EMPTY() {
        // given
        int pageNo = 1;

        // when
        CoverletterDto.ListRes res = service.getList(member, new Paging(pageNo));

        // then
        assertNotNull(res);
        assertTrue(res.getCoverletters().isEmpty());
    }

    @Test
    public void 자기소개서_저장() throws IOException {
        // given
        File file = new File("src/test/java/com/nexters/rezoom/coverletter/CoverletterNew.json");
        CoverletterDto.SaveReq req = new ObjectMapper().readValue(file, CoverletterDto.SaveReq.class);

        // when
        long savedId = service.save(member, req);

        // then
        Coverletter findCoverletter = repository.findById(member, savedId);

        assertEquals(req.getCompanyName(), findCoverletter.getCompanyName());
        assertEquals(req.getQuestions().size(), findCoverletter.getQuestions().size());
        findCoverletter.getQuestions().forEach(question -> {
            assertNotEquals(question.getId(), 0);
            assertTrue(question.getTitle().contains("test"));
            assertTrue(question.getContents().contains("test"));

            question.getHashtags().forEach(hashTag -> assertNotEquals(hashTag.getId(), 0));
        });
    }

    // @Test
    // 테스트하기가 어렵다.
    public void 자기소개서_문항_해시태그_수정() {
    }

    @Test(expected = RuntimeException.class)
    public void 자기소개서_삭제() {
        // given
        Coverletter coverletter = new Coverletter(member, "testCompany");
        repository.save(coverletter);

        // when
        service.delete(member, coverletter.getId());

        // then
        CoverletterDto.ViewRes res = service.getView(member, coverletter.getId());
        // expected runtimeException
    }

    @Test(expected = RuntimeException.class)
    public void 자기소개서_없는거_삭제할때_RuntimeException() {
        // given
        long coverletterId = 223241L;

        // when
        service.delete(member, coverletterId);

        // then
        CoverletterDto.ViewRes res = service.getView(member, coverletterId);
        // expected runtimeException
    }

}
