package com.nexters.rezoom.coverletter;

import com.nexters.rezoom.coverletter.domain.Deadline;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by momentjin@gmail.com on 2019-08-14
 * Github : http://github.com/momentjin
 */
public class DeadlineTest {

    @Test
    @DisplayName("마감일 파싱 성공")
    public void test1() {
        Deadline deadline = new Deadline("2019-08-05 13:00");
        System.out.println(deadline.getDeadline());
    }

    @Test()
    @DisplayName("마감일 파싱시 포멧 오류나면 null을 반환해야 한다.")
    public void test2() {
        Deadline deadline = new Deadline("2019-08-05T13:00");
        assertNull(deadline.getDeadline());
    }

    @Test
    @DisplayName("마감일이 현재보다 과거면 마감일 초과")
    public void test3() {
        Deadline deadline = new Deadline("2019-05-10 19:00");
        assertTrue(deadline.isExpired());
    }

    @Test
    @DisplayName("마감일이 현재보다 과거면 마감일 미초과")
    public void test4() {
        Deadline deadline = new Deadline(LocalDateTime.now().plusDays(1));
        assertFalse(deadline.isExpired());
    }
}
