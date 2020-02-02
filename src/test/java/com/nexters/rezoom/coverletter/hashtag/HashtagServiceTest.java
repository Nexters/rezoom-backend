package com.nexters.rezoom.coverletter.hashtag;

import com.nexters.global.exception.BusinessException;
import com.nexters.rezoom.coverletter.application.HashtagService;
import com.nexters.rezoom.coverletter.domain.Hashtag;
import com.nexters.rezoom.coverletter.domain.HashtagRepository;
import com.nexters.rezoom.member.domain.Member;
import com.nexters.util.TestObjectUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

/**
 * Created by momentjin@gmail.com on 2019-06-14
 * Github : http://github.com/momentjin
 */

@SpringBootTest(classes = {HashtagService.class})
@ExtendWith(SpringExtension.class)
public class HashtagServiceTest {

    private static Member member;
    @Autowired
    private HashtagService service;
    @MockBean
    private HashtagRepository repository;

    @BeforeAll
    public static void createMember() {
        member = TestObjectUtils.createTestMember();
    }

    @Test
    public void 문항에_속하지_않는_해시태그는_조회되지_않는다() {
        // given
        Hashtag hashtag = new Hashtag("tag");

        given(repository.findAllByMember(any(Member.class)))
                .willReturn(Collections.singletonList(hashtag));

        // when
        List<String> findHashtagsRegisteredInQuestion = service.getMyHashtags(member);

        // then
        assertTrue(findHashtagsRegisteredInQuestion.isEmpty());
    }

    @Test
    public void 해시태그_조회_실패시_BusinessException() {
        // given
        String tagValue = UUID.randomUUID().toString();

        // when & then
        when(repository.findByMemberAndValue(any(), any()))
                .thenReturn(null);

        assertThrows(BusinessException.class, () -> {
            service.getHashTag(member, tagValue);
        });
    }

}
