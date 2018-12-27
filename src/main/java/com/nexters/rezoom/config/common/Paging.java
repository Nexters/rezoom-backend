package com.nexters.rezoom.config.common;

import lombok.Getter;


@Getter
public class Paging {

    private static int MIN_PAGE_NUMBER = 1;
    private static int MAX_NUMBER_PER_PAGE = 10;

    private int pageNo;
    private int beginRow;
    private int numberPerPage = MAX_NUMBER_PER_PAGE;

    public Paging(int pageNo) {
        this.setPageNo(pageNo);
        this.setBeginRow();
    }

    private void setPageNo(int pageNo) {
        if (pageNo <= 0) {
            this.pageNo = MIN_PAGE_NUMBER;
            return;
        }

        this.pageNo = pageNo;
    }

    private void setBeginRow() {
        this.beginRow = MAX_NUMBER_PER_PAGE * (this.pageNo - 1);
    }


}
