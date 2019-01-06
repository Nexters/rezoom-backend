package com.nexters.rezoom.coverletter;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class DomainTest {

    private Member member;

    public DomainTest() {
        member = new Member("test@test.com", "재언진", "ppswrord");
    }

    // Coverletter에 Question을 추가하면 Question에서도 Coverletter를 참조할 수 있다.
    @Test
    public void Coverletter_Question_연관관계_테스트() {
        // given
        Coverletter coverletter = new Coverletter(member, "companyName");
        Question question = new Question("title1", "contents1");

        // when
        coverletter.setQuestions(Collections.singletonList(question));

        // test
        assertEquals(coverletter, question.getCoverletter());
    }

    // Question에 Coverletter를 추가하면 Coverletter에서도 Question을 참조할 수 있다.
    @Test
    public void Coverletter_Question_연관관계_테스트_역방향() {
//        // given
//        Coverletter coverletter = new Coverletter(member, "companyName");
//        Question question = new Question("title1", "contents1");
//
//        // when
//        question.setCoverletter(coverletter);
//
//        // test
//        assertTrue(coverletter.getQuestions().contains(question));
    }

}
