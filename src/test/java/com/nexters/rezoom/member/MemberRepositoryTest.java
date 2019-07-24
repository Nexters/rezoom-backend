package com.nexters.rezoom.member;

import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.member.domain.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Created by momentjin@gmail.com on 2019-07-24
 * Github : http://github.com/momentjin
 */
@Transactional
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository repository;


    @Test
    @DisplayName("id로 Member Entity를 조회할 수 있다")
    public void memberSelectTest1() {
        // given
        Member member = new Member("test@test.test", "tester", "test");
        repository.save(member);

        // when
        Member findMember = repository.findById(member.getId());

        // then
        assertEquals(findMember.getId(), member.getId());
    }

    @Test
    @DisplayName("없는 id로 Member Entity를 조회하면 NULL을 반환한다")
    public void memberSelectTest2() {
        // given
        String randomId = UUID.randomUUID().toString();

        // when
        Member member = repository.findById(randomId);

        // then
        assertNull(member);
    }

    @Test
    @DisplayName("회원 정보를 수정할 수 있다")
    public void memberUpdateTest1() {
        // given
        Member member = new Member("test@test.test", "tester", "test");
        repository.save(member);

        String newName = UUID.randomUUID().toString().substring(0, 3);

        // when
        member.setName(newName);
        repository.save(member);

        // then
        Member updatedMember = repository.findById(member.getId());
        assertEquals(member.getName(), updatedMember.getName());
    }

}
