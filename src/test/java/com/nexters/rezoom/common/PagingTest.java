package com.nexters.rezoom.common;

import com.nexters.rezoom.config.common.Paging;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PagingTest {

    @Test
    public void 페이지번호가_0이하일때_기본값_설정() {
        // given
        int pageNo = -1;

        // when
        Paging paging = new Paging(pageNo);

        // then
        assertEquals(paging.getPageNo(), 1);
        assertEquals(paging.getBeginRow(), 0);
        assertEquals(paging.getNumberPerPage(), 10);
    }

    @Test
    public void 페이지가_자연수면_BeginRow는_이전페이지_곱하기_10() {
        // given
        int pageNo = 5;

        // when
        Paging paging = new Paging(pageNo);

        // then
        assertEquals(paging.getBeginRow(), (pageNo - 1) * 10);
    }

}
