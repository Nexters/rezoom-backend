package com.nexters.rezoom.coverletter;

import com.nexters.rezoom.coverletter.domain.Deadline;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

/**
 * Created by momentjin@gmail.com on 2019-03-22
 * Github : http://github.com/momentjin
 **/

public class DeadlineTest {

    @Test
    public void 데드라인_만료여부_테스트() {
        // given
        // 현재일 기준 5분 전을 만료일시로 가정한다.
        LocalDateTime deadlineDt = LocalDateTime.now().minusMinutes(5);
        Deadline deadline = new Deadline(deadlineDt);

        // when
        boolean isExpired = deadline.isExpired();

        // then
        assertTrue(isExpired);
    }

}
