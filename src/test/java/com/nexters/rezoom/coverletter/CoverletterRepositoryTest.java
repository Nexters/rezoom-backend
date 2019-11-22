package com.nexters.rezoom.coverletter;

import com.nexters.rezoom.coverletter.domain.*;
import com.nexters.rezoom.coverletter.dto.PageRequest;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.util.TestObjectUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Created by momentjin@gmail.com on 2019-03-26
 * Github : http://github.com/momentjin
 **/

@SpringBootTest
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
    @DisplayName("자기소개서 정보가 정상적으로 저장되어야 한다.")
    @Transactional
    public void coverletterSaveTest1() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);

        // when
        repository.save(coverletter);

        // then
        Optional<Coverletter> findCoverletterOpt = repository.findByIdAndMember(coverletter.getId(), member);
        Coverletter findCoverletter = findCoverletterOpt.get();

        assertEquals(coverletter.getId(),               findCoverletter.getId());
        assertEquals(coverletter.getCompanyName(),      findCoverletter.getCompanyName());
        assertEquals(coverletter.getApplicationYear(),  findCoverletter.getApplicationYear());
        assertEquals(coverletter.getApplicationHalf(),  findCoverletter.getApplicationHalf());
        assertEquals(coverletter.getApplicationType(),  findCoverletter.getApplicationType());
        assertEquals(coverletter.getJobType(),          findCoverletter.getJobType());
        assertEquals(coverletter.getDeadline(),         findCoverletter.getDeadline());
    }

    @Test
    @DisplayName("자기소개서를 저장하면, 문항도 저장되어야 한다")
    @Transactional
    public void coverletterSaveTest2() {
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
    @DisplayName("자기소개서 내 문항에 태그가 포함되어 있으면 함께 저장해야 한다")
    @Transactional
    public void coverletterSaveTest3() {
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
    @DisplayName("id가 있는 자기소개서를 저장하면 수정되어야 한다")
    @Transactional
    public void coverletterUpdateTest1() {
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
    @DisplayName("자기소개서 수정시 문항을 삭제하면, DB에서도 제거해야 한다")
    @Transactional
    public void coverletterUpdateTest2() {
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
    @DisplayName("삭제된 자기소개서를 조회하면 NULL을 반환한다")
    public void coverletterDeleteTest1() {
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
    @DisplayName("문항이 포함된 자기소개서를 삭제하면 문항도 삭제되어야 한다")
    public void coverletterDeleteTest2() {
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
    @DisplayName("문항이 있는 자기소개서를 조회하면, 문항도 같이 조회되어야 한다.")
    @Transactional
    public void coverletterSelectTest1() {
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
    @DisplayName("태그가 포함된 문항이 있는 자기소개서를 조회하면, 태그도 같이 조회되어야 한다")
    @Transactional
    public void coverletterSelectTest2() {
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
    @DisplayName("자기소개서 리스트 조회 페이징 테스트")
    @Transactional
    public void coverletterSelectTest3() {
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
    @DisplayName("자기소개서 조회시 없으면 NULL을 반환해야 한다")
    public void coverletterSelectTest4() {
        // given
        long coverletterID = -1;

        // when
        Optional<Coverletter> findCoverletterOpt = repository.findByIdAndMember(coverletterID, member);

        // then
        assertNull(findCoverletterOpt.orElse(null));
    }

    @Test
    @DisplayName("자기소개서 리스트 조회시 없으면 EMPTY를 반환해야 한다")
    public void coverletterSelectTest5() {
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
    public void coverletterSelectTest6() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        repository.save(coverletter);

        // when
        List<Coverletter> coverletters = repository.findAllByDeadlineGreaterThanEqual(Deadline.now());

        // then
        assertTrue(coverletters.size() >= 1);
    }
}
