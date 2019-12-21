package com.nexters.rezoom.coverletter.question;

import com.nexters.RezoomApplication;
import com.nexters.rezoom.coverletter.domain.*;
import com.nexters.rezoom.coverletter.infra.JpaQuestionRepository;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.util.TestObjectUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by momentjin@gmail.com on 2019-06-12
 * Github : http://github.com/momentjin
 */


@Sql(scripts = "classpath:data.sql")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(classes = {JpaQuestionRepository.class})
@ContextConfiguration(classes = RezoomApplication.class)
@ExtendWith(SpringExtension.class)
public class QuestionRepositoryTest {

    private static Member member;
    @Autowired
    private QuestionRepository repository;
    @Autowired
    private CoverletterRepository coverletterRepository;

    @BeforeAll
    public static void createMember() {
        member = new Member("test", "", "");
    }

    @Test
    public void 문항을_조회하면_자기소개서도_조회된다() {
        // given
        Coverletter savedCoverletter = saveCoverletter();
        long savedQuestionId = savedCoverletter.getQuestions().get(0).getId();

        // when
        Question findQuestion = repository.findByKey(savedQuestionId, member);

        // then
        Coverletter findCoverletter = findQuestion.getCoverletter();
        assertEquals(savedCoverletter, findCoverletter);
    }

    @Test
    @Transactional
    public void 문항을_조회하면_태그도_조회된다() {
        // given
        Coverletter savedCoverletter = saveCoverletter();
        long savedQuestionId = savedCoverletter.getQuestions().get(0).getId();

        // when
        Question findQuestion = repository.findByKey(savedQuestionId, member);

        // then
        Question reqQuestion = savedCoverletter.getQuestions().stream()
                .filter(question -> question.getId() == savedQuestionId)
                .findAny().get();

        Set<Hashtag> reqHashtags = reqQuestion.getHashtags();
        Set<Hashtag> findHashtags = findQuestion.getHashtags();

        assertEquals(findHashtags.size(), reqHashtags.size());
    }

    @Test
    public void 문항_조회_실패시_NULL() {
        // given
        long findQuestionId = -1;

        // when
        Question findQuestion = repository.findByKey(findQuestionId, member);

        // then
        assertNull(findQuestion);
    }

    private Coverletter saveCoverletter() {
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        coverletterRepository.save(coverletter);

        if (coverletter.getId() == 0 || coverletter.getQuestions().size() == 0)
            fail();

        long savedQuestionId = coverletter.getQuestions().get(0).getId();
        if (savedQuestionId == 0)
            fail(); // id가 0이면 저장이 안됐다는 말과 같다.

        return coverletter;
    }
}
