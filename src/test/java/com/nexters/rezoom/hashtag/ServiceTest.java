package com.nexters.rezoom.hashtag;

import com.nexters.rezoom.dto.HashTagDto;
import com.nexters.rezoom.dto.QuestionDto;
import com.nexters.rezoom.hashtag.application.HashtagService;
import com.nexters.rezoom.hashtag.domain.HashTag;
import com.nexters.rezoom.member.domain.Member;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.Assert.*;

@Transactional
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceTest {

    /**
     * TODO : 개선 필요 (데이터를 검증하는 메소드를 만들고 @BeforeClass로 동작시켜 검증하거나, 그럴 필요가 없게 만들거나)
     * 테스트 데이터가 삽입된 상태에서만 테스트 정상 동작
     * member_id = admin@admin.admin
     * coverletter_id = 15 (testCompany)
     * question_id = 17, 18 (testTitle, testContents)
     * hashtag_id = 30, 31 (testTag, testTag)
     */

    @Autowired
    private HashtagService service;

    @PersistenceContext
    private EntityManager em;

    private Member member;

    @Before
    public void init() {
        member = new Member("admin@admin.admin", "admin", "admin");
    }

    @Test
    public void 나의_해시태그_조회() {
        // given

        // when
        List<HashTagDto.ViewRes> res = service.getMyHashtags(member);

        // then
        assertEquals(2, res.size());
        res.forEach(viewRes -> assertTrue(viewRes.getValue().contains("testTag")));
    }

    @Test
    public void 해시태그가_등록된_문항조회() {
        // given
        HashTag findHashTag = new HashTag(member, "testTag1");

        // when
        List<QuestionDto.ViewRes> res = service.getQuestionsRelatedHashtag(member, findHashTag);

        // then
        res.forEach(viewRes -> {
            assertTrue(viewRes.getTitle().contains("testTitle"));
            assertTrue(viewRes.getContents().contains("testContents"));
        });
    }

    @Test
    public void 해시태그_1개_수정() {
        // given
        HashTagDto.UpdateReq req = new HashTagDto.UpdateReq("testTag1", "updateTestTag1");

        // when
        service.modifyHashTag(member, req);

        // then
        HashTag updatedHashTag = em.find(HashTag.class, 31L);
        assertEquals(updatedHashTag.getValue(), req.getUpdatedValue());
    }

    @Test
    public void 해시태그_1개_삭제() {
        // given

        // when
        service.removeHashTag(member, "testTag1");

        // then
        HashTag findHashTag = em.find(HashTag.class, 31L);
        assertNull(findHashTag);
    }

}
