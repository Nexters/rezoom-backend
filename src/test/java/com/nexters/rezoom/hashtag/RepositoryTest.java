package com.nexters.rezoom.hashtag;

import com.nexters.rezoom.hashtag.domain.HashTag;
import com.nexters.rezoom.hashtag.domain.HashTagRepository;
import com.nexters.rezoom.member.domain.Member;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RepositoryTest {

    /**
     * TODO : 개선 필요 (데이터베이스 내 데이터 의존적 테스트 해결하기..)
     */

    @Autowired
    private HashTagRepository repository;

    private Member member;

    @Before
    public void init() {
        member = new Member("admin@admin.admin", "admin", "admin");
    }

    @Test
    public void 해쉬태그__1개_조회_UK() {
        // given

        // when
        HashTag hashTag = repository.findByKey(member, "testTag1");

        // then
        assertEquals(hashTag.getValue(), "testTag1");
        assertEquals(hashTag.getQuestions().size(), 1);
    }

    @Test
    public void 나의_해쉬태그_전체_조회() {
        // given

        // when
        List<HashTag> hashTags = repository.findAll(member);

        // then
        assertEquals(hashTags.size(), 2);
        hashTags.forEach(hashTag -> assertTrue(hashTag.getValue().contains("testTag")));
    }

    @Test
    public void 해쉬태그_1개_삭제() {
        // given
        String hashTagValue = "testTag1";
        HashTag hashTag = repository.findByKey(member, hashTagValue);

        // when
        repository.delete(hashTag);

        // then
        HashTag findHashTag = repository.findByKey(member, hashTagValue);
        assertNull(findHashTag);
    }


}
