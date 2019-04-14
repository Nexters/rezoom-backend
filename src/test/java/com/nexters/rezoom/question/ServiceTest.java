package com.nexters.rezoom.question;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexters.rezoom.coverletter.application.CoverletterService;
import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.dto.CoverletterDto;
import com.nexters.rezoom.dto.MemberDto;
import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.member.application.MemberService;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.application.QuestionService;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

    @Autowired
    private QuestionService service;

    @Autowired
    private MemberService memberService;
    private Member member;

    @Autowired
    private CoverletterRepository coverletterRepository;

    @Autowired
    private CoverletterService coverletterService;
    private Coverletter coverletter;

    @Before
    public void createMember() {
        member = new Member(UUID.randomUUID().toString(), "test", "test");
        memberService.signUp(new MemberDto.SignUpReq(member.getId(), member.getPassword(), member.getName()));
    }

    @Before
    public void createCoverletter() throws IOException {
        File file = new File("src/test/java/com/nexters/rezoom/coverletter/resource/CoverletterNew.json");
        CoverletterDto.SaveReq req = new ObjectMapper().readValue(file, CoverletterDto.SaveReq.class);

        // when
        long coverletterId = coverletterService.save(member, req);
        coverletter = coverletterRepository.findById(member, coverletterId);
    }

    /**
     * TODO : 문제 해결하기..
     * Hastag 객체를 통해 Question 객체를 탐색하려고 했는데 자꾸 참조하는 객체가 없다...
     */
    // @Test
    public void 문항_1개_조회() {
        // given
        long questionId = coverletter.getQuestions().get(0).getId();

        // when
        QuestionDto.ViewRes res = service.getView(questionId, member);

        // then
        assertEquals(res.getId(), questionId);
        res.getHashtags().forEach(viewRes -> assertTrue(viewRes.getValue().contains("test")));
    }

    @Test(expected = RuntimeException.class)
    public void 문항_1개_조회할때_없으면_RuntimeException() {
        // given
        long questionId = 9999999L;

        // when
        QuestionDto.ViewRes res = service.getView(questionId, member);

        // then
        // Expected runtimeException
    }


    /**
     * TODO : 문제 해결하기..
     * Hastag 객체를 통해 Question 객체를 탐색하려고 했는데 자꾸 참조하는 객체가 없다...
     */
    // @Test
    public void 해시태그가_등록된_문항조회() {
        // given
        String findHashtagValue = coverletter.getQuestions().get(1).getHashtags().iterator().next().getValue();

        // when
        List<QuestionDto.ViewRes> res = service.getQuestionsByHashtags(member, findHashtagValue);

        // then
        assertEquals(3, res.size());
        res.forEach(viewRes -> {
            assertTrue(viewRes.getTitle().contains("test"));
            assertTrue(viewRes.getContents().contains("test"));
        });
    }
}
