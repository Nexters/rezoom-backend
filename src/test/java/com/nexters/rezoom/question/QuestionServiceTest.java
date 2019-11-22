package com.nexters.rezoom.question;

import com.nexters.config.exception.EntityNotFoundException;
import com.nexters.rezoom.coverletter.application.CoverletterService;
import com.nexters.rezoom.coverletter.application.QuestionService;
import com.nexters.rezoom.coverletter.dto.CoverletterDto;
import com.nexters.rezoom.coverletter.dto.QuestionDto;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.util.TestObjectUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by momentjin@gmail.com on 2019-06-12
 * Github : http://github.com/momentjin
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class QuestionServiceTest {

    @Autowired
    private QuestionService service;

    @Autowired
    private CoverletterService coverletterService;

    private static Member member;

    @BeforeAll
    public static void createMember() {
        member = new Member("test", "", "");
    }

    @Test
    @DisplayName("없는 문항을 조회하면 EntityNotFoundException이 발생한다")
    public void questionSelectTest1() {
        // given
        long findQuestionId = -1;

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            service.getView(findQuestionId, member);
        });
    }

    // 저장된 자기소개서의 문항을 찾으려면 ID를 알아야 하는데, 현재로썬 알 수 있는 방법이 없음.. ㅠㅠ
    // @Test
    @DisplayName("문항을 조회하면 태그도 함께 조회되어야 한다")
    @Transactional
    public void questionSelectTest2() {
        // given
        CoverletterDto.SaveReq coverletter = TestObjectUtils.createCoverletterSaveReqDto();
        coverletterService.save(member, coverletter);

        // when
        QuestionDto.ViewRes question = service.getView(1L, member);

        // then
        Set<String> hashtags = question.getHashtags();
        assertNotNull(hashtags);
        assertTrue(hashtags.size() >= 1);
    }

    @Test
    @DisplayName("태그를 통해 문항을 조회할 수 있어야 한다")
    public void questionSelectTest3() {
        // given
        CoverletterDto.SaveReq coverletter = TestObjectUtils.createCoverletterSaveReqDto();
        long savedCoverletterId = coverletterService.save(member, coverletter);

        String tagValue = "tag2(new)";

        // when
        List<QuestionDto.ViewRes> questions = service.getQuestionsByHashtags(member, Collections.singletonList(tagValue));

        // then
        assertTrue(questions.size() >= 2);

        // @transactional 문제로 삭제 로직 추가
        // coverletterService.delete(member, savedCoverletterId);
    }

}
