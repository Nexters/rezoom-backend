package com.nexters.rezoom.core.domain.coverletter.question;

import com.nexters.rezoom.core.domain.coverletter.application.HashtagService;
import com.nexters.rezoom.core.domain.coverletter.application.QuestionService;
import com.nexters.rezoom.core.domain.coverletter.domain.Coverletter;
import com.nexters.rezoom.core.domain.coverletter.domain.Hashtag;
import com.nexters.rezoom.core.domain.coverletter.domain.Question;
import com.nexters.rezoom.core.domain.coverletter.domain.QuestionRepository;
import com.nexters.rezoom.core.global.exception.BusinessException;
import com.nexters.rezoom.core.domain.member.domain.Member;
import com.nexters.rezoom.util.TestObjectUtils;
import com.nexters.rezoom.core.domain.coverletter.api.dto.QuestionDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

/**
 * Created by momentjin@gmail.com on 2019-06-12
 * Github : http://github.com/momentjin
 */

@SpringBootTest(classes = QuestionService.class)
@ExtendWith(SpringExtension.class)
public class QuestionServiceTest {

    private static Member member;
    @Autowired
    private QuestionService service;
    @MockBean
    private QuestionRepository questionRepository;
    @MockBean
    private HashtagService hashtagService;

    @BeforeAll
    public static void createMember() {
        member = TestObjectUtils.createTestMember();
    }

    @Test
    public void 문항_조회_실패시_BusinessException() {
        // given
        given(questionRepository.findByKey(anyLong(), any(Member.class)))
                .willReturn(null);

        // when & then
        assertThrows(BusinessException.class, () -> {
            service.getView(anyLong(), any(Member.class));
        });
    }

    @Test
    public void 문항을_조회하면_태그도_조회된다() {
        // given
        Question question = new Question("title", "question", new HashSet<>(Collections.singletonList(new Hashtag("tag1"))));
        question.setCoverletter(Coverletter.builder()
                .member(member)
                .companyName("test company")
                .build());

        given(questionRepository.findByKey(anyLong(), any(Member.class)))
                .willReturn(question);

        // when
        QuestionDto.ViewRes questionRes = service.getView(1L, member);

        // then
        Set<String> hashtags = questionRes.getHashtags();
        assertEquals(1, hashtags.size());
    }

    @Test
    public void 태그로_문항을_조회할_수_있다() {
        // given
        Question q1 = new Question("title1", "contents1");
        Question q2 = new Question("title2", "contents2");
        Hashtag hashtag = new Hashtag("tag1");

        q1.setHashtags(new HashSet<>(Collections.singletonList(hashtag)));
        q2.setHashtags(new HashSet<>(Collections.singletonList(hashtag)));
        hashtag.setQuestions(Arrays.asList(q1, q2));

        Coverletter coverletter = Coverletter.builder()
                .id(10L)
                .companyName("test company")
                .build();

        coverletter.setQuestions(Arrays.asList(q1, q2));

        given(hashtagService.getHashTag(any(Member.class), eq(hashtag.getValue())))
                .willReturn(hashtag);

        // when
        List<QuestionDto.ViewRes> questions = service.getQuestionsByHashtags(member, Collections.singletonList(hashtag.getValue()));

        // then
        assertEquals(2, questions.size());
        questions.forEach(question -> assertTrue(question.getHashtags().contains(hashtag.getValue())));
    }

}
