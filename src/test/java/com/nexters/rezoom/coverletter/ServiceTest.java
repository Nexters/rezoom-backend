package com.nexters.rezoom.coverletter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.rezoom.config.common.Paging;
import com.nexters.rezoom.coverletter.application.CoverletterService;
import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.dto.CoverletterDto;
import com.nexters.rezoom.dto.MemberDto;
import com.nexters.rezoom.hashtag.domain.HashTagRepository;
import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.member.application.MemberService;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import com.nexters.rezoom.util.TestObjectUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;
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
    private HashTagRepository hashtagRepository;

    @Autowired
    private MemberService memberService;
    private Member member;

    @Before
    public void createMember() {
        member = new Member(UUID.randomUUID().toString(), "test", "test");
        memberService.signUp(new MemberDto.SignUpReq(member.getId(), member.getPassword(), member.getName()));
    }

    // ---------------------------------------------------------

    @Test
    public void 자기소개서_단건조회() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        repository.save(coverletter);

        // when
        CoverletterDto.ViewRes res = service.getView(member, coverletter.getId());

        // then
        assertEquals(res.getId(), coverletter.getId());
        assertEquals(res.getCompanyName(), coverletter.getCompanyName());
        assertEquals(res.getQuestions().size(), 2);
        assertEquals(res.getApplicationHalf(), coverletter.getApplicationHalf().getTypeNo());
        assertEquals(res.getApplicationType(), coverletter.getApplicationType().getTypeNo());
        assertEquals(res.getApplicationYear(), coverletter.getApplicationYear().getValue());
    }

    @Test
    public void 자기소개서_리스트_조회() {
        // given
        Coverletter coverletter1 = TestObjectUtils.createCoverletter(member);
        Coverletter coverletter2 = TestObjectUtils.createCoverletter(member);
        repository.save(coverletter1);
        repository.save(coverletter2);

        // when
        CoverletterDto.ListRes res = service.getList(member, new Paging(1));

        // then
        assertEquals(res.getCoverletters().size(), 2);
    }

    /**
     * 새로운 자기소개서를 생성하고 저장했을 때
     */
    @Test
    public void 신규_자기소개서_저장() throws IOException {
        // given
        File file = new File("src/test/java/com/nexters/rezoom/coverletter/resource/CoverletterNew.json");
        CoverletterDto.SaveReq req = new ObjectMapper().readValue(file, CoverletterDto.SaveReq.class);

        // when
        long coverletterId = service.save(member, req);

        // then
        Coverletter findCoverletter = repository.findById(member, coverletterId);
        assertEquals(req.getCompanyName(), findCoverletter.getCompanyName());
        for (Question q : findCoverletter.getQuestions()) {
            assertNotEquals(q.getId(), 0);
            assertTrue(q.getTitle().contains("test"));
            assertTrue(q.getContents().contains("test"));

            for (Hashtag h : q.getHashtags()) {
                assertNotEquals(h.getId(), 0);
            }
        }
    }

    /**
     * TODO : 복잡하고 부정확한 테스트 개선하기
     * 기존재하는 자기소개서를 수성하고 저장했을 때
     */
    @Test
    public void 수정된_자기소개서_저장() throws IOException {
        // given
        File file = new File("src/test/java/com/nexters/rezoom/coverletter/resource/CoverletterNew.json");
        CoverletterDto.SaveReq req = new ObjectMapper().readValue(file, CoverletterDto.SaveReq.class);
        long coverletterId = service.save(member, req);

        File file2 = new File("src/test/java/com/nexters/rezoom/coverletter/resource/CoverletterUpdate.json");
        CoverletterDto.UpdateReq updateReq = new ObjectMapper().readValue(file2, CoverletterDto.UpdateReq.class);
        updateReq.setId(coverletterId);

        // when
        service.update(member, updateReq);

        // then
        Coverletter findCoverletter = repository.findById(member, coverletterId);

        assertEquals(coverletterId, findCoverletter.getId());
        assertEquals(updateReq.getCompanyName(), findCoverletter.getCompanyName());
        for (Question q : findCoverletter.getQuestions()) {
            assertNotEquals(q.getId(), 0);
            assertTrue(q.getTitle().contains("updated"));
            assertTrue(q.getContents().contains("updated"));

            for (Hashtag h : q.getHashtags()) {
                assertNotEquals(h.getId(), 0);
            }
        }

        // 기존에 존재하는 해시태그는 모두 존재해야 한다.
        // 자기소개서를 수정하면서 새롭게 추가된 해시태그도 존재해야 한다.
        List<Hashtag> myHashtags = hashtagRepository.findAll(member);
        assertTrue(myHashtags.size() >= 5);
    }

    @Test
    public void 자기소개서_삭제() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletter(member);
        repository.save(coverletter);

        // when
        CoverletterDto.ViewRes findCoverletter = service.getView(member, coverletter.getId());
        service.delete(member, findCoverletter.getId());

        // then
        Coverletter result = repository.findById(member, findCoverletter.getId());
        assertNull(result);
    }

    // ----------------------------------------------------------

    @Test(expected = RuntimeException.class)
    public void 자기소개서_삭제_없으면_RuntimeException() {
        // given
        long coverletterId = 223241L;

        // when
        service.delete(member, coverletterId);

        // then
        CoverletterDto.ViewRes res = service.getView(member, coverletterId);
        // expected runtimeException
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
    public void 자기소개서_리스트조회_없으면_EMPTY() {
        // given
        int pageNo = 1;

        // when
        CoverletterDto.ListRes res = service.getList(member, new Paging(pageNo));

        // then
        assertNotNull(res);
        assertTrue(res.getCoverletters().isEmpty());
    }
}
