package com.nexters.rezoom.hashtag;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.rezoom.coverletter.application.CoverletterService;
import com.nexters.rezoom.dto.CoverletterDto;
import com.nexters.rezoom.dto.HashTagDto;
import com.nexters.rezoom.dto.MemberDto;
import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.hashtag.application.HashtagService;
import com.nexters.rezoom.hashtag.domain.HashTag;
import com.nexters.rezoom.hashtag.domain.HashTagRepository;
import com.nexters.rezoom.member.application.MemberService;
import com.nexters.rezoom.member.domain.Member;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

    @Autowired
    private HashtagService service;

    @Autowired
    private HashTagRepository repository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MemberService memberService;
    private Member member;

    @Autowired
    private CoverletterService coverletterService;

    @Before
    public void createMember() {
        member = new Member(UUID.randomUUID().toString(), "test", "test");
        memberService.signUp(new MemberDto.SignUpReq(member.getId(), member.getPassword(), member.getName()));
    }

    @Before
    public void createCoverletter() throws IOException {
        File file = new File("src/test/java/com/nexters/rezoom/coverletter/CoverletterNew.json");
        CoverletterDto.SaveReq req = new ObjectMapper().readValue(file, CoverletterDto.SaveReq.class);
        coverletterService.save(member, req);
    }

    @Test
    public void 나의_해시태그_조회() {
        // given

        // when
        List<HashTagDto.ViewRes> res = service.getMyHashtags(member);

        // then
        assertEquals(res.size(), 3);

        res.forEach(viewRes -> {
            HashTag hashTag = repository.findByKey(member, viewRes.getValue());
            assertTrue(hashTag.getQuestions().size() > 0);
        });
    }

    @Test
    public void 해시태그가_등록된_문항조회() {
        // given

        // when
        List<QuestionDto.ViewRes> res = service.getQuestionsRelatedHashtag(member, "해시태그1");

        // then
        res.forEach(viewRes -> {
            assertTrue(viewRes.getTitle().contains("testTitle"));
            assertTrue(viewRes.getContents().contains("testContents"));
        });
    }

    @Test
    public void 해시태그_1개_수정() {
        // given
        HashTagDto.UpdateReq req = new HashTagDto.UpdateReq("해시태그1", "해시태그11");

        // when
        service.modifyHashTag(member, req);

        // then
        List<QuestionDto.ViewRes> res = service.getQuestionsRelatedHashtag(member, "해시태그11");

        res.forEach(viewRes -> {
            assertTrue(viewRes.getTitle().contains("testTitle"));
            assertTrue(viewRes.getContents().contains("testContents"));
        });
    }

    @Test
    public void 해시태그_1개_삭제() {
        // given

        // when
        service.removeHashTag(member, "해시태그1");

        // then
        List<HashTagDto.ViewRes> hashTags = service.getMyHashtags(member);
        assertTrue(hashTags.stream().noneMatch(viewRes -> viewRes.getValue().equals("해시태그1")));
    }

}
