package com.nexters.rezoom.question;

import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.application.QuestionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

    @Autowired
    private QuestionService service;

    @Test
    public void 문항_1개_조회() {
        // given
        long questionId = 17L;
        Member member = new Member("admin@admin.admin", "", "");

        // when
        QuestionDto.ViewRes res = service.getView(questionId, member);

        // then
        assertEquals(res.getId(), questionId);
        res.getHashtags().forEach(viewRes -> assertTrue(viewRes.getValue().contains("testTag")));
    }

    @Test(expected = RuntimeException.class)
    public void 문항_1개_조회할때_없으면_RuntimeException() {
        // given
        long questionId = 1L;
        Member member = new Member("admin@admin.admin", "", "");

        // when
        QuestionDto.ViewRes res = service.getView(questionId, member);

        // then
        // Expected runtimeException
    }
}
