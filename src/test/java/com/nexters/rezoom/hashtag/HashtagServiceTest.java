package com.nexters.rezoom.hashtag;

import com.nexters.rezoom.config.exception.EntityNotFoundException;
import com.nexters.rezoom.coverletter.domain.Coverletter;
import com.nexters.rezoom.coverletter.domain.CoverletterRepository;
import com.nexters.rezoom.hashtag.application.HashtagService;
import com.nexters.rezoom.hashtag.domain.Hashtag;
import com.nexters.rezoom.hashtag.domain.HashtagRepository;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.rezoom.util.TestObjectUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by momentjin@gmail.com on 2019-06-14
 * Github : http://github.com/momentjin
 */

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class HashtagServiceTest {

    @Autowired
    private HashtagService service;

    @Autowired
    private HashtagRepository repository;

    @Autowired
    private CoverletterRepository coverletterRepository;

    private static Member member;

    @BeforeAll
    public static void createMember() {
        member = new Member("test", "", "");
    }

    @Test
    @DisplayName("나의 해시태그 리스트엔 문항에 최소 1개 이상 등록된 태그만 포함되어야 한다.")
    @Transactional
    public void hashtagSelectTest() {
        // given
        Coverletter coverletter = TestObjectUtils.createCoverletterHasQuestionAndHashtag(member);
        coverletterRepository.save(coverletter);

        Hashtag hashtag = new Hashtag(member, UUID.randomUUID().toString());
        repository.save(hashtag);

        // when
        List<String> findHashtagsRegisteredInQuestion = service.getMyHashtags(member);

        // then
        List<String> findAllHashtags = repository.findAll(member).stream()
                                        .map(Hashtag::getValue)
                                        .collect(Collectors.toList());

        System.out.println("문항에 1개라도 등록된 태그 갯수 : " + findHashtagsRegisteredInQuestion.size());
        System.out.println("저장한 모든 태그 갯수 :  " + findAllHashtags.size());

        assertTrue(findAllHashtags.size() > findHashtagsRegisteredInQuestion.size());
    }

    @Test
    @DisplayName("해시태그 리스트 조회시 등록된 태그가 없으면 EMPTY를 반환한다")
    public void hashtagSelectTest2() {
        // given
        Member anonymous = new Member(UUID.randomUUID().toString(), "tester", "password");

        // when
        List<String> findHashtags = service.getMyHashtags(anonymous);

        // then
        assertTrue(findHashtags.isEmpty());
    }

    @Test
    @DisplayName("없는 해시태그를 조회하면 EntityNotFound Exception")
    public void hastagSelectTest3() {
        // given
        String tagValue = UUID.randomUUID().toString();

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            service.getHashTag(member, tagValue);
        });
    }

}
