package com.nexters.rezoom.hashtag;

import com.nexters.rezoom.dto.MemberDto;
import com.nexters.rezoom.hashtag.domain.HashTag;
import com.nexters.rezoom.hashtag.domain.HashTagRepository;
import com.nexters.rezoom.member.application.MemberService;
import com.nexters.rezoom.member.domain.Member;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RepositoryTest {

    @Autowired
    private HashTagRepository repository;

    @Autowired
    private MemberService memberService;
    private Member member;

    @Before
    public void createMember() {
        member = new Member(UUID.randomUUID().toString(), "test", "test");
        memberService.signUp(new MemberDto.SignUpReq(member.getId(), member.getPassword(), member.getName()));
    }

    @Test
    public void 해쉬태그__1개_조회_UK() {
        // given
        String currentDate = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        String value = currentDate + "test";
        HashTag hashTag = new HashTag(member, value);
        repository.save(hashTag);

        // when
        HashTag findHashTag = repository.findByKey(member, value);

        // then
        assertEquals(findHashTag.getValue(), value);
    }

    @Test
    public void 나의_해쉬태그_전체_조회() {
        // given
        String currentDate = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        String value = currentDate + "test1";
        HashTag hashTag1 = new HashTag(member, value);
        repository.save(hashTag1);

        String value2 = currentDate + "test2";
        HashTag hashTag2 = new HashTag(member, value2);
        repository.save(hashTag2);

        // when
        List<HashTag> hashtags = repository.findAll(member);

        // then
        assertEquals(2, hashtags.size());
        assertEquals(hashTag1, hashtags.get(0));
        assertEquals(hashTag2, hashtags.get(1));
    }

    @Test
    public void 나의_해쉬태그_전체_조회_없으면_EMPTY() {
        // given

        // when
        List<HashTag> hashtags = repository.findAll(member);

        // then
        assertTrue(hashtags.isEmpty());
    }


    @Test
    public void 해쉬태그_1개_삭제() {
        // given
        String currentDate = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        String value = currentDate + "test";
        HashTag hashtag = new HashTag(member, value);
        repository.save(hashtag);

        HashTag findHashtag = repository.findByKey(member, hashtag.getValue());

        // when
        repository.delete(findHashtag);

        // then
        HashTag findHashTag = repository.findByKey(member, findHashtag.getValue());
        assertNull(findHashTag);
    }

}
