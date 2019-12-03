package com.nexters.rezoom.coverletter;

import com.nexters.rezoom.coverletter.domain.Deadline;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by momentjin@gmail.com on 2019-08-14
 * Github : http://github.com/momentjin
 */

public class DeadlineTest {

    @Test
    public void 마감일_파싱_성공() {
        Deadline deadline = new Deadline("2019-08-05 13:00");
        System.out.println(deadline.getDeadline());
    }

    @Test
    public void 마감일_파싱_오류시_NULL_반환() {
        Deadline deadline = new Deadline("2019-08-05T13:00");
        assertNull(deadline.getDeadline());
    }

    @Test
    public void 마감일_초과_테스트() {
        Deadline deadline = new Deadline("2019-05-10 19:00");
        assertTrue(deadline.isExpired());
    }

    @Test
    public void 마감일_미초과_테스트() {
        Deadline deadline = new Deadline(LocalDateTime.now().plusDays(1));
        assertFalse(deadline.isExpired());
    }
}
