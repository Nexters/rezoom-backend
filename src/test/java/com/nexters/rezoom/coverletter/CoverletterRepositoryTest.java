package com.nexters.rezoom.coverletter;

import com.nexters.RezoomApplication;
import com.nexters.rezoom.coverletter.domain.*;
import com.nexters.rezoom.coverletter.dto.PageRequest;
import com.nexters.rezoom.coverletter.infra.JpaQuestionRepository;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.util.TestObjectUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Created by momentjin@gmail.com on 2019-03-26
 * Github : http://github.com/momentjin
 **/

@Sql(scripts = "classpath:data.sql")
@DataJpaTest
@SpringBootTest(classes = JpaQuestionRepository.class)
@ContextConfiguration(classes = RezoomApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
public class CoverletterRepositoryTest {

    @Autowired
    private CoverletterRepository repository;

    @Autowired
    private QuestionRepository questionRepository;

    private static Member member;

    @BeforeAll
    public static void createMember() {
        member = new Member("test", "", "");
    }

    @Test
    @Transactional
    public void 자기소개서_정보_저장_테스트() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);

        // when
        repository.save(coverletter);

        // then
        Optional<Coverletter> findCoverletterOpt = repository.findByIdAndMember(coverletter.getId(), member);
        Coverletter findCoverletter = findCoverletterOpt.orElse(null);

        assertNotNull(findCoverletter);
        assertEquals(coverletter.getId(),               findCoverletter.getId());
        assertEquals(coverletter.getCompanyName(),      findCoverletter.getCompanyName());
        assertEquals(coverletter.getApplicationYear(),  findCoverletter.getApplicationYear());
        assertEquals(coverletter.getApplicationHalf(),  findCoverletter.getApplicationHalf());
        assertEquals(coverletter.getApplicationType(),  findCoverletter.getApplicationType());
        assertEquals(coverletter.getJobType(),          findCoverletter.getJobType());
        assertEquals(coverletter.getDeadline(),         findCoverletter.getDeadline());
    }

    @Test
    @Transactional
    public void 자기소개서_저장시_문항도_저장된다() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);

        // when
        repository.save(coverletter);

        // then
        Optional<Coverletter> findCoverletterOpt = repository.findByIdAndMember(coverletter.getId(), member);
        Coverletter findCoverletter = findCoverletterOpt.get();

        List<Question> findQuestions = findCoverletter.getQuestions();
        List<Question> questions = coverletter.getQuestions();

        assertEquals(questions.size(), findQuestions.size());
        for (int i = 0; i < findQuestions.size(); i++) {
            assertEquals(findQuestions.get(i), questions.get(i));
        }
    }

    @Test
    @Transactional
    public void 자기소개서_저장시_태그도_저장된다() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        List<Question> questions = coverletter.getQuestions();

        // when
        repository.save(coverletter);

        // then
        Optional<Coverletter> findCoverletterOpt = repository.findByIdAndMember(coverletter.getId(), member);
        Coverletter findCoverletter = findCoverletterOpt.get();

        List<Question> findQuestions = findCoverletter.getQuestions();

        assertTrue(findQuestions.size() >= 1);

        for (int i = 0; i < findQuestions.size(); i++) {
            Question findQuestion = findQuestions.get(i);
            Question question = questions.get(i);

            Set<Hashtag> findHashtags = findQuestion.getHashtags();
            Set<Hashtag> hashtags = question.getHashtags();

            assertEquals(findHashtags.size(), hashtags.size());

            Iterator<Hashtag> findIterator = findHashtags.iterator();
            Iterator<Hashtag> iterator = hashtags.iterator();

            while (findIterator.hasNext()) {
                Hashtag findHashtag = findIterator.next();
                Hashtag hashtag = iterator.next();

                assertEquals(findHashtag, hashtag);
            }
        }
    }

    @Test
    @Transactional
    public void 자기소개서_수정_테스트() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        repository.save(coverletter);

        // when
        coverletter.setCompanyName("updatedTestCompany");

        // then
        Optional<Coverletter> findCoverletterOpt = repository.findByIdAndMember(coverletter.getId(), member);
        Coverletter findCoverletter = findCoverletterOpt.get();

        assertEquals(coverletter.getId(), findCoverletter.getId());
        assertEquals(coverletter.getCompanyName(), findCoverletter.getCompanyName());
    }

    @Test
    @Transactional
    public void 자기소개서_수정시_문항삭제하면_DB에서도_제거된다() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        repository.save(coverletter);

        List<Question> questions = coverletter.getQuestions();
        Question question = questions.get(0);
        questions.remove(question);

        // when
        repository.save(coverletter);

        // then
        Question findQuestion = questionRepository.findByKey(question.getId(), member);
        assertNull(findQuestion);
    }

    @Transactional
    @Test
    public void 없는_자기소개서_조회시_NULL을_반환한다() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        repository.save(coverletter);

        // when
        repository.delete(coverletter);

        // then
        Optional<Coverletter> findCoverletterOpt = repository.findByIdAndMember(coverletter.getId(), member);
        assertNull(findCoverletterOpt.orElse(null));
    }

    @Transactional
    @Test
    public void 문항이_포함된_자기소개서_삭제시_문항도_삭제된다() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        repository.save(coverletter);

        // when
        Optional<Coverletter> findCoverletterOpt = repository.findByIdAndMember(coverletter.getId(), member);
        Coverletter existCoverletter = findCoverletterOpt.get();
        repository.delete(existCoverletter);

        // then
        List<Question> existQuestions = existCoverletter.getQuestions();
        for (Question q : existQuestions) {
            Question findQuestion = questionRepository.findByKey(q.getId(), member);
            assertNull(findQuestion);
        }
    }

    @Test
    @Transactional
    public void 자기소개서_조회시_문항도_조회된다() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        repository.save(coverletter);

        // when
        Optional<Coverletter> findCoverletterOpt = repository.findByIdAndMember(coverletter.getId(), member);
        Coverletter findCoverletter = findCoverletterOpt.get();

        // then
        List<Question> findQuestions = findCoverletter.getQuestions();
        assertNotNull(findQuestions);
        assertTrue(findQuestions.size() > 0);
    }

    @Test
    @Transactional
    public void 자기소개서_조회시_태그도_조회된다() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        repository.save(coverletter);

        // when
        Optional<Coverletter> findCoverletterOpt = repository.findByIdAndMember(coverletter.getId(), member);
        Coverletter findCoverletter = findCoverletterOpt.get();

        // then
        List<Question> findQuestions = findCoverletter.getQuestions();

        for (Question q : findQuestions) {
            Set<Hashtag> findHashtags = q.getHashtags();
            assertNotNull(findHashtags);
            assertTrue(findHashtags.size() > 0);
        }
    }

    @Test
    @Transactional
    public void 자기소개서_리스트_조회_페이징_테스트() {
        // given
        Coverletter coverletter0 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        Coverletter coverletter1 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        Coverletter coverletter2 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        Coverletter coverletter3 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        Coverletter coverletter4 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        Coverletter coverletter5 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        Coverletter coverletter6 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        Coverletter coverletter7 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        Coverletter coverletter8 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        Coverletter coverletter9 = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);

        repository.save(coverletter0);
        repository.save(coverletter1);
        repository.save(coverletter2);
        repository.save(coverletter3);
        repository.save(coverletter4);
        repository.save(coverletter5);
        repository.save(coverletter6);
        repository.save(coverletter7);
        repository.save(coverletter8);
        repository.save(coverletter9);

        // when
        int numberPerPage = 5;
        PageRequest request = new PageRequest();
        request.setSize(numberPerPage);

        Page<Coverletter> coverletters = repository.findAllByMember(request.of(), member);

        // then
        assertEquals(coverletters.getContent().size(), numberPerPage);
    }

    @Test
    public void 자기소개서_조회시_없으면_NULL을_반환한다() {
        // given
        long coverletterID = -1;

        // when
        Optional<Coverletter> findCoverletterOpt = repository.findByIdAndMember(coverletterID, member);

        // then
        assertNull(findCoverletterOpt.orElse(null));
    }

    @Test
    public void 자기소개서_리스트_조회시_Empty_반환() {
        // given
        Member anonymous = new Member(UUID.randomUUID().toString(), "", "");

        // when
        Page<Coverletter> coverletters = repository.findAllByMember(new PageRequest().of(), anonymous);

        // then
        assertFalse(coverletters.hasContent());
    }

    @Transactional
    @Test
    @DisplayName("마감일이 있고, 마감일이 현재포함 미래이고, 지원하지 않은 모든 자기소개서를 조회한다")
    public void coverletterSelectTest() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        repository.save(coverletter);

        // when
        List<Coverletter> coverletters = repository.findAllByDeadlineGreaterThanEqual(Deadline.now());

        // then
        assertTrue(coverletters.size() >= 1);
    }
}
