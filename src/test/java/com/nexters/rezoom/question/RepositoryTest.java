package com.nexters.rezoom.question;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.dto.MemberDto;
import com.nexters.rezoom.member.application.MemberService;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import com.nexters.rezoom.question.domain.QuestionRepository;
import com.nexters.rezoom.util.TestObjectUtils;
import org.junit.Before;
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
    private QuestionRepository repository;

    @Autowired
    private MemberService memberService;
    private Member member;

    @Autowired
    private CoverletterRepository coverletterRepository;
    private Coverletter coverletter;

    @Before
    public void createMember() {
        member = new Member(UUID.randomUUID().toString(), "test", "test");
        memberService.signUp(new MemberDto.SignUpReq(member.getId(), member.getPassword(), member.getName()));
    }

    @Before
    public void createCoverletter() {
        coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        coverletterRepository.save(coverletter);
    }

    @Test
    public void 문항_1개_조회() {
        // given
        long questionId = coverletter.getQuestions().get(0).getId();

        // when
        Question findQuestion = repository.findByKey(questionId, member);

        // then
        assertEquals(findQuestion.getId(), questionId);
    }

    @Test
    public void 문항조회_결과값이_없으면_NULL() {
        // given
        long questionId = 99999999L;

        // when
        Question findQuestion = repository.findByKey(questionId, member);

        // then
        assertNull(findQuestion);
    }

}
