package com.nexters.rezoom.coverletter;

import com.nexters.config.exception.EntityNotFoundException;
import com.nexters.rezoom.coverletter.application.CoverletterService;
import com.nexters.rezoom.coverletter.domain.*;
import com.nexters.rezoom.coverletter.dto.CoverletterDto;
import com.nexters.rezoom.coverletter.dto.QuestionDto;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.util.TestObjectUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {CoverletterService.class})
@ExtendWith(SpringExtension.class)
public class CoverletterServiceTest {

    private static Member member;

    @Autowired
    private CoverletterService service;

    @MockBean
    private CoverletterRepository repository;

    @MockBean
    private HashtagRepository hashtagRepository;

    @BeforeAll
    public static void setup() {
        member = new Member("test", "", "");
    }

    @Test
    public void 자기소개서_정보_저장() {

        // given
        CoverletterDto.SaveReq saveReq = TestObjectUtils.createCoverletterSaveReqDto();

        Coverletter coverletter = saveReq.toEntity();
        coverletter.setQuestions(saveReq.getQuestions().stream()
                .map(QuestionDto.SaveReq::toEntity)
                .collect(Collectors.toList()));

        given(repository.findByIdAndMember(coverletter.getId(), member))
                .willReturn(Optional.of(coverletter));

        // when
        when(repository.save(any()))
                .thenReturn(Coverletter.builder().id(coverletter.getId()).build());

        service.save(member, saveReq);

        // then
        CoverletterDto.ViewRes viewRes = service.getView(member, coverletter.getId());

        assertEquals(viewRes.getId(), coverletter.getId());
        assertEquals(viewRes.getCompanyName(), saveReq.getCompanyName());
        assertEquals(viewRes.getApplicationHalf(), saveReq.getApplicationHalf());
        assertEquals(viewRes.getApplicationYear(), Year.of(saveReq.getApplicationYear()));
        assertEquals(viewRes.getDeadline(), saveReq.getDeadline());
        assertEquals(viewRes.getIsApplication(), saveReq.getIsApplication());
        assertEquals(viewRes.getIsPass(), saveReq.getIsPass());
        assertEquals(viewRes.getJobType(), saveReq.getJobType());
    }

    @Test
    public void 자기소개서를_저장하면_문항도_저장된다() {

        // given
        CoverletterDto.SaveReq saveReq = TestObjectUtils.createCoverletterSaveReqDto();

        Coverletter coverletter = saveReq.toEntity();
        coverletter.setQuestions(saveReq.getQuestions().stream()
                .map(QuestionDto.SaveReq::toEntity)
                .collect(Collectors.toList()));

        given(repository.findByIdAndMember(coverletter.getId(), member))
                .willReturn(Optional.of(coverletter));

        given(repository.save(any()))
                .willReturn(Coverletter.builder().id(coverletter.getId()).build());

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
    public void 자기소개서를_저장하면_해시태그도_저장된다() {
        // given
        CoverletterDto.SaveReq saveReq = TestObjectUtils.createCoverletterSaveReqDto();

        Coverletter coverletter = saveReq.toEntity();
        coverletter.setQuestions(saveReq.getQuestions().stream()
                .map(QuestionDto.SaveReq::toEntity)
                .collect(Collectors.toList()));

        given(repository.findByIdAndMember(coverletter.getId(), member))
                .willReturn(Optional.of(coverletter));

        // when
        when(repository.save(any()))
                .thenReturn(Coverletter.builder().id(coverletter.getId()).build());

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
    public void 자기소개서를_조회하면_문항도_조회된다() {
        // given
        List<Question> questions = Arrays.asList(
                new Question("title1", "contents1"),
                new Question("title2", "contents2"));

        Coverletter coverletter = Coverletter.builder()
                .companyName("mock test")
                .build();

        coverletter.setQuestions(questions);

        given(repository.findByIdAndMember(coverletter.getId(), member))
                .willReturn(Optional.of(coverletter));

        // when
        CoverletterDto.ViewRes resultCoverletter = service.getView(member, coverletter.getId());

        // then
        List<QuestionDto.ViewRes> resultQuestions = resultCoverletter.getQuestions();
        assertEquals(2, resultQuestions.size());
    }

    @Test
    public void 자기소개서를_조회하면_태그도_조회된다() {
        // given
        Hashtag h1 = new Hashtag("tag1");
        Hashtag h2 = new Hashtag("tag2");
        Hashtag h3 = new Hashtag("tag3");

        List<Question> questions = Arrays.asList(
                new Question("title1", "contents1", new HashSet<>(Arrays.asList(h1, h2))),
                new Question("title2", "contents2", new HashSet<>(Arrays.asList(h2, h3))));

        Coverletter coverletter = Coverletter.builder()
                .companyName("mock test")
                .build();

        coverletter.setQuestions(questions);

        given(repository.findByIdAndMember(coverletter.getId(), member))
                .willReturn(Optional.of(coverletter));

        // when

        CoverletterDto.ViewRes resultCoverletter = service.getView(member, coverletter.getId());

        // then
        List<QuestionDto.ViewRes> resultQuestions = resultCoverletter.getQuestions();

        for (QuestionDto.ViewRes q : resultQuestions) {
            Set<String> findHashtags = q.getHashtags();
            assertNotNull(findHashtags);
            assertTrue(findHashtags.size() > 0);
        }
    }

    @Test
    public void 조회된_자기소개서가_NULL아면_EntityNotFoundException() {
        // given
        long findCoverletterId = -1;

        given(repository.findByIdAndMember(findCoverletterId, member)).willReturn(Optional.empty());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            service.getView(member, findCoverletterId);
        });
    }

}
