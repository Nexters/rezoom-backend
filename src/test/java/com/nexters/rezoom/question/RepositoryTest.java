package com.nexters.rezoom.question;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import com.nexters.rezoom.question.domain.QuestionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RepositoryTest {

    @Autowired
    private QuestionRepository repository;

    private Member member;

    @Before
    public void init() {
        member = new Member("admin@admin.admin", "", "");
    }

    // @Test
    public void 문항_1개_조회() {
        // given
        long questionId = 17L; // member가 소유하고 있는 문항 번호

        // when
        Question findQuestion = repository.findByKey(questionId, member);

        // then
        assertEquals(findQuestion.getId(), questionId);
    }

    @Test
    public void 문항조회_결과값이_없으면_NULL() {
        // given
        long questionId = 1L; // member의 소유가 아닌 문항 번호

        // when
        Question findQuestion = repository.findByKey(questionId, member);

        // then
        assertNull(findQuestion);
    }

}
