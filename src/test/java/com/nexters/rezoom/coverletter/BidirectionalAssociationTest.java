package com.nexters.rezoom.coverletter;

import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.question.domain.Question;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by momentjin@gmail.com on 2019-03-27
 * Github : http://github.com/momentjin
 * <p>
 * 양방향관계 테스트
 **/
public class BidirectionalAssociationTest {

    @Test
    public void Coverletter_Question_테스트() {
        // given
        Coverletter coverletter = Coverletter.builder().companyName("testCompany").build();
        Question question = new Question("title", "contents");
        Question question2 = new Question("title2", "contents2");


        // when
        coverletter.setQuestions(Arrays.asList(question, question2));

        // then
        Coverletter findCoverletter = question.getCoverletter();
        assertEquals(coverletter, findCoverletter);
        assertEquals(coverletter.getQuestions().size(), 2);
    }

    @Test
    public void Coverletter_Question_테스트_역방향() {
        // given
        Coverletter coverletter = Coverletter.builder().build();
        Question question = new Question("title1", "contents");
        Question question2 = new Question("title1", "contents");

        // when
        question.setCoverletter(coverletter);
        question2.setCoverletter(coverletter);

        // then
        assertTrue(coverletter.getQuestions().contains(question));
        assertEquals(coverletter.getQuestions().size(), 2);
    }

}
