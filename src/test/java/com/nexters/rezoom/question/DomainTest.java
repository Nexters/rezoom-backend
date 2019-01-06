package com.nexters.rezoom.question;

import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.question.domain.Question;

import static org.junit.Assert.assertTrue;

public class DomainTest {

    private Member member;

    public DomainTest() {
        member = new Member("test@test.com", "재언진", "ppswrord");
    }

    // Question에 Hashtag를 추가하면 Hashtag에서도 Question을 참조할 수 있다.
    // @Test
    public void Question_Hashtag_연관관계_테스트1() {
        // given
        Question question = new Question("title1", "contents1");
        Hashtag hashTag = new Hashtag(member, "해쉬태그1");

        // when
        //question.addHashtag(hashTag);

        // then
        assertTrue(hashTag.getQuestions().contains(question));
    }

    // Hashtag에 Question을 추가하면 Question에서도 Hashtag를 참조할 수 있다.
    // @Test
    public void Question_Hashtag_연관관계_테스트2() {
        // given
        Question question = new Question("title1", "contents1");
        Hashtag hashTag = new Hashtag(member, "해쉬태그1");

        // when
        //hashTag.addQuestion(question);

        // then
        assertTrue(question.getHashtags().contains(hashTag));
    }
}
