package com.nexters.rezoom.coverletter.hashtag;

import com.nexters.rezoom.coverletter.domain.Hashtag;
import com.nexters.rezoom.coverletter.domain.HashtagRepository;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.util.TestObjectUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by momentjin@gmail.com on 2019-06-14
 * Github : http://github.com/momentjin
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
public class HashtagRepositoryTest {

    private static Member member;
    @Autowired
    private HashtagRepository repository;

    @BeforeAll
    public static void createMember() {
        member = TestObjectUtils.createTestMember();
    }

    @Test
    public void 태그_단건_조회시_없으면_NULL() {
        // given
        String hashtagValue = UUID.randomUUID().toString();

        // when
        Optional<Hashtag> findHashtag = repository.findByMemberAndValue(member, hashtagValue);

        // then
        assertNull(findHashtag);
    }

    @Test
    public void 태그_리스트_조회시_없으면_EMPTY() {
        // given
        Member anonymous = new Member(UUID.randomUUID().toString(), "", "");

        // when
        List<Hashtag> findHashtags = repository.findAllByMember(anonymous);

        // then
        assertTrue(findHashtags.isEmpty());
    }


}
