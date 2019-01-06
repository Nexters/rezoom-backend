package com.nexters.rezoom.hashtag;

import com.nexters.rezoom.dto.MemberDto;
import com.nexters.rezoom.hashtag.domain.HashTagRepository;
import com.nexters.rezoom.hashtag.domain.Hashtag;
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
        Hashtag hashTag = new Hashtag(member, value);
        repository.save(hashTag);

        // when
        Hashtag findHashtag = repository.findByKey(member, value);

        // then
        assertEquals(findHashtag.getValue(), value);
    }

    @Test
    public void 나의_해쉬태그_전체_조회() {
        // given
        String currentDate = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        String value = currentDate + "test1";
        Hashtag hashtag1 = new Hashtag(member, value);
        repository.save(hashtag1);

        String value2 = currentDate + "test2";
        Hashtag hashtag2 = new Hashtag(member, value2);
        repository.save(hashtag2);

        // when
        List<Hashtag> hashtags = repository.findAll(member);

        // then
        assertEquals(2, hashtags.size());
        assertEquals(hashtag1, hashtags.get(0));
        assertEquals(hashtag2, hashtags.get(1));
    }

    @Test
    public void 나의_해쉬태그_전체_조회_없으면_EMPTY() {
        // given

        // when
        List<Hashtag> hashtags = repository.findAll(member);

        // then
        assertTrue(hashtags.isEmpty());
    }


    @Test
    public void 해쉬태그_1개_삭제() {
        // given
        String currentDate = new java.text.SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
        String value = currentDate + "test";
        Hashtag hashtag = new Hashtag(member, value);
        repository.save(hashtag);

        Hashtag findHashtag = repository.findByKey(member, hashtag.getValue());

        // when
        repository.delete(findHashtag);

        // then
        Hashtag findHashTag = repository.findByKey(member, findHashtag.getValue());
        assertNull(findHashTag);
    }

}
