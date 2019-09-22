package com.nexters.rezoom.coverletter;

import com.nexters.rezoom.config.exception.EntityNotFoundException;
import com.nexters.rezoom.coverletter.application.CoverletterService;
import com.nexters.rezoom.coverletter.domain.ApplicationHalf;
import com.nexters.rezoom.coverletter.domain.IsApplication;
import com.nexters.rezoom.coverletter.domain.IsPass;
import com.nexters.rezoom.coverletter.dto.CoverletterDto;
import com.nexters.rezoom.hashtag.application.HashtagService;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.dto.QuestionDto;
import com.nexters.rezoom.util.TestObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CoverletterServiceTest {

    private static Member member;

    @Autowired
    private CoverletterService service;

    @Autowired
    private HashtagService hashtagService;

    @BeforeAll
    public static void createMember() {
        member = new Member("test", "", "");
    }

    @Test
    @DisplayName("자기소개서 정보가 정상적으로 저장되어야 한다")
    @Transactional
    public void coverletterSaveTest1() {
        // given
        CoverletterDto.SaveReq saveReq = TestObjectUtils.createCoverletterSaveReqDto();

        // when
        long savedCoverletterId = service.save(member, saveReq);

        // then
        CoverletterDto.ViewRes viewRes = service.getView(member, savedCoverletterId);

        assertEquals(viewRes.getId(), savedCoverletterId);
        assertEquals(viewRes.getCompanyName(), "testCompany");
        assertEquals(viewRes.getApplicationHalf(), ApplicationHalf.FIRST_HALF);
        assertEquals(viewRes.getApplicationYear(), Year.of(2019));
        assertEquals(viewRes.getDeadline(), LocalDateTime.of(2019, 4, 30, 18, 0));
        assertEquals(viewRes.getIsApplication(), IsApplication.YES);
        assertEquals(viewRes.getIsPass(), IsPass.PASS);
        assertEquals(viewRes.getJobType(), "backend engineer");
    }

    @Test
    @DisplayName("자기소개서를 저장하면, 문항도 저장되어야 한다")
    @Transactional
    public void coverletterSaveTest2() {
        // given
        CoverletterDto.SaveReq saveReq = TestObjectUtils.createCoverletterSaveReqDto();

        // when
        long savedCoverletterId = service.save(member, saveReq);

        // then
        CoverletterDto.ViewRes viewRes = service.getView(member, savedCoverletterId);
        List<QuestionDto.ViewRes> questions = viewRes.getQuestions();

        assertEquals(questions.size(), saveReq.getQuestions().size());
        for (QuestionDto.ViewRes question : questions) {
            assertNotNull(question.getTitle());
            assertNotNull(question.getContents());
        }
    }

    @Test
    @DisplayName("자기소개서를 저장하면, 태그도 저장되어야 한다")
    @Transactional
    public void coverletterSaveTest3() {
        // given
        CoverletterDto.SaveReq saveReq = TestObjectUtils.createCoverletterSaveReqDto();

        // when
        long savedCoverletterId = service.save(member, saveReq);

        // then
        CoverletterDto.ViewRes viewRes = service.getView(member, savedCoverletterId);
        List<QuestionDto.ViewRes> questions = viewRes.getQuestions();

        for (QuestionDto.ViewRes question : questions) {
            Set<String> hashtags = question.getHashtags();

            assertNotNull(hashtags);
            assertTrue(hashtags.size() >= 1); // 테스트 데이터의 모든 문항은 최소 1개의 태그를 갖고 있다.
            hashtags.forEach(Assertions::assertNotNull);
        }
    }

    @Test
    @DisplayName("DB에 저장된 해쉬태그와 같은 Value를 가지는 Hashtag는 중복 저장되지 않는다")
    // @Transactional - 문제..
    public void coverletterSaveTest4() {
        // given
        CoverletterDto.SaveReq saveReq = TestObjectUtils.createCoverletterSaveReqDto();
        long savedCoverletterId = service.save(member, saveReq);

        List<String> hashtags = hashtagService.getMyHashtags(member);
        int beforeHashtagSize = hashtags.size();

        // when
        // 처음 저장했던 데이터와 같은 데이터를 저장한다.
        long savedCoverletterId2 = service.save(member, saveReq);

        // then
        assertTrue(beforeHashtagSize >= 1); // 테스트 데이터의 모든 문항은 최소 1개의 태그를 갖고 있다.

        List<String> hashtags2 = hashtagService.getMyHashtags(member);
        assertEquals(beforeHashtagSize, hashtags2.size());

        // 임시 코드
        service.delete(member, savedCoverletterId);
        service.delete(member, savedCoverletterId2);
    }

    @Test
    @DisplayName("문항이 있는 자기소개서를 조회하면, 문항도 같이 조회되어야 한다.")
    @Transactional
    public void coverletterSelectTest1() {
        // given
        long coverletterId = 1;

        // when
        CoverletterDto.ViewRes coverletter = service.getView(member, coverletterId);

        // then
        List<QuestionDto.ViewRes> questions = coverletter.getQuestions();
        assertNotNull(questions);
        assertTrue(questions.size() > 0);
    }

    @Test
    @DisplayName("태그가 포함된 문항이 있는 자기소개서를 조회하면, 태그도 같이 조회되어야 한다")
    @Transactional
    public void coverletterSelectTest2() {
        // given
        long coverletterId = 1;

        // when
        CoverletterDto.ViewRes coverletter = service.getView(member, coverletterId);

        // then
        List<QuestionDto.ViewRes> questions = coverletter.getQuestions();

        for (QuestionDto.ViewRes q : questions) {
            Set<String> findHashtags = q.getHashtags();
            assertNotNull(findHashtags);
            assertTrue(findHashtags.size() > 0);
        }
    }

    @Test
    @DisplayName("자기소개서 조회시 없으면 EntityNotFoundException 발생")
    public void coverletterSelectTest3() {
        // given
        long findCoverletterId = -1;

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            service.getView(member, findCoverletterId);
        });
    }

}
