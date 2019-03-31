package com.nexters.rezoom.coverletter;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.dto.MemberDto;
import com.nexters.rezoom.hashtag.domain.HashTagRepository;
import com.nexters.rezoom.hashtag.domain.Hashtag;
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

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by momentjin@gmail.com on 2019-03-26
 * Github : http://github.com/momentjin
 **/

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RepositoryTest {

    @Autowired
    private CoverletterRepository repository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private HashTagRepository hashTagRepository;

    @Autowired
    private MemberService memberService;

    private Member member;

    // ---------------------------------------------

    @Before
    public void createMember() {
        member = new Member(UUID.randomUUID().toString(), "test", "test");
        memberService.signUp(new MemberDto.SignUpReq(member.getId(), member.getName(), member.getPassword()));
    }

    // ---------------------------------------------

    /**
     * 저장 테스트
     * <p>
     * Coverletter 저장시, CASCADE 속성으로 인해 다른 객체(Question, Hashtag)들도 함께 저장된다.
     */
    @Test
    public void 자기소개서_저장_테스트() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);

        // when
        repository.save(coverletter);

        // then
        assertTrue(coverletter.getId() > 0);

        Coverletter findCoverletter = repository.findById(member, coverletter.getId());
        assertEquals(findCoverletter.getQuestions().size(), 2);
        assertEquals(findCoverletter.getQuestions().get(0).getHashtags().size(), 1);
        assertEquals(findCoverletter.getQuestions().get(1).getHashtags().size(), 3);
    }

    /**
     * 삭제 테스트
     * <p>
     * Coverletter 삭제시, CASCADE 속성으로 인해 Question도 함께 삭제된다.
     * Hashtag는 함께 삭제되지 않는다.
     */
    @Test
    public void 자기소개서_삭제_테스트() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        repository.save(coverletter);

        // when
        Coverletter findCoverletter = repository.findById(member, coverletter.getId());
        repository.delete(findCoverletter);

        // then
        findCoverletter = repository.findById(member, findCoverletter.getId());
        assertNull(findCoverletter);

        for (Question q : coverletter.getQuestions()) {
            Question findQuestion = questionRepository.findByKey(q.getId(), member);
            assertNull(findQuestion);
        }

        for (Question q : coverletter.getQuestions()) {
            for (Hashtag h : q.getHashtags()) {
                Hashtag findHashtag = hashTagRepository.findByKey(member, h.getValue());
                assertNotNull(findHashtag);
            }
        }
    }

    @Test
    public void 자기소개서_단건조회_테스트() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletter(member);
        repository.save(coverletter);

        // when
        Coverletter findCoverletter = repository.findById(member, coverletter.getId());

        // then
        assertNotNull(findCoverletter);
        assertEquals(coverletter, findCoverletter);
        assertEquals(coverletter.getCompanyName(), findCoverletter.getCompanyName());
    }

    // TODO : 테스트 방안 모색
    public void 자기소개서_리스트조회_테스트() {

    }

    // ------------------------------------------------

    @Test
    public void coverletter_단건조회_없으면_NULL() {
        // given
        long coverletterID = 99999L;

        // when
        Coverletter findCoverletter = repository.findById(member, coverletterID);

        // then
        assertNull(findCoverletter);
    }

    @Test
    public void coverletter_리스트즈회_없으면_EMPTY() {
        // given

        // when
        List<Coverletter> coverletters = repository.findAll(member, 0, 100);

        // then
        assertTrue(coverletters.isEmpty());
    }
}
