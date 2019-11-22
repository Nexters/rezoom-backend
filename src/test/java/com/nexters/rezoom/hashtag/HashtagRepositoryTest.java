package com.nexters.rezoom.hashtag;

import com.nexters.rezoom.coverletter.domain.Hashtag;
import com.nexters.rezoom.coverletter.domain.HashtagRepository;
import com.nexters.rezoom.member.domain.Member;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by momentjin@gmail.com on 2019-06-14
 * Github : http://github.com/momentjin
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class HashtagRepositoryTest {

    @Autowired
    private HashtagRepository repository;


    private static Member member;

    @BeforeAll
    public static void createMember() {
        member = new Member("test", "", "");
    }

    @Test
    @DisplayName("태그 단건 조회시 없으면 NULL을 반환한다")
    public void hashtagSelectTest1() {
        // given
        String hashtagValue = UUID.randomUUID().toString();

        // when
        Hashtag findHashtag = repository.findByMemberAndValue(member, hashtagValue);

        // then
        assertNull(findHashtag);
    }

    @Test
    @DisplayName("태그 리스트 조회시 없으면 EMPTY를 반환한다")
    public void hashtagSelectTest2() {
        // given
        Member anonymous = new Member(UUID.randomUUID().toString(), "tester", "password");

        // when
        List<Hashtag> findHashtags = repository.findAllByMember(anonymous);

        // then
        assertTrue(findHashtags.isEmpty());
    }


}
