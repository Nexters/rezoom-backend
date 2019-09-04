package com.nexters.rezoom.question;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import com.nexters.rezoom.question.domain.QuestionRepository;
import com.nexters.rezoom.util.TestObjectUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by momentjin@gmail.com on 2019-06-12
 * Github : http://github.com/momentjin
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class QuestionRepositoryTest {

    @Autowired
    private CoverletterRepository coverletterRepository;

    @Autowired
    private QuestionRepository repository;

    private static Member member;

    @BeforeAll
    public static void createMember() {
        member = new Member("test", "", "");
    }

    @Test
    @DisplayName("문항을 조회하면 자기소개서 정보도 함께 조회되어야 한다.")
    @Transactional
    public void questionSelectTest1() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        coverletterRepository.save(coverletter);

        if (coverletter.getId() == 0 || coverletter.getQuestions().size() == 0)
            fail();

        long savedQuestionId = coverletter.getQuestions().get(0).getId();
        if (savedQuestionId == 0)
            fail(); // id가 0이면 저장이 안됐다는 말과 같다.

        // when
        Question findQuestion = repository.findByKey(savedQuestionId, member);

        // then
        Coverletter findCoverletter = findQuestion.getCoverletter();
        assertEquals(coverletter, findCoverletter);
    }

    @Test
    @DisplayName("문항을 조회하면 태그도 함께 조회되어야 한다.")
    @Transactional
    public void questionSelectTest2() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        coverletterRepository.save(coverletter);

        if (coverletter.getId() == 0 || coverletter.getQuestions().size() == 0)
            fail();

        long savedQuestionId = coverletter.getQuestions().get(0).getId();
        if (savedQuestionId == 0)
            fail(); // id가 0이면 저장이 안됐다는 말과 같다.

        // when
        Question findQuestion = repository.findByKey(savedQuestionId, member);

        // then
        Question reqQuestion = coverletter.getQuestions().stream().filter(question -> question.getId() == savedQuestionId).findAny().get();
        Set<Hashtag> reqHashtags = reqQuestion.getHashtags();
        Set<Hashtag> findHashtags = findQuestion.getHashtags();

        assertEquals(findHashtags.size(), reqHashtags.size());
    }

    @Test
    @DisplayName("없는 문항을 조회하면 NULL을 반환한다")
    public void questionSelectTest3() {
        // given
        long findQuestionId = -1;

        // when
        Question findQuestion = repository.findByKey(findQuestionId, member);

        // then
        assertNull(findQuestion);
    }
}
