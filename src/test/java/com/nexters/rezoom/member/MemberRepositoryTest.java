package com.nexters.rezoom.member;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import com.nexters.util.TestObjectUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by momentjin@gmail.com on 2019-07-24
 * Github : http://github.com/momentjin
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
public class MemberRepositoryTest {

    private static Member member;

    @Autowired
    private MemberRepository repository;

    @BeforeAll
    public static void setup() {
        member = TestObjectUtils.createTestMember();
    }

    @Test
    public void 멤버_조회_성공() {
        // given
        repository.save(member);

        // when
        Optional<Member> findMemberOpt = repository.findById(member.getId());
        Member findMember = findMemberOpt.orElse(null);

        // then
        assertNotNull(findMember);
        assertEquals(findMember.getId(), member.getId());
    }

    @Test
    public void 멤버_조회시_없으면_NULL() {
        // given
        String randomId = UUID.randomUUID().toString();

        // when
        Optional<Member> findMemberOpt = repository.findById(randomId);

        // then
        assertFalse(findMemberOpt.isPresent());
    }


}
