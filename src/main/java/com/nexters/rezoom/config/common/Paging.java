package com.nexters.rezoom.config.common;

import lombok.Getter;


@Getter
public class Paging {

    private static int DEFAULT_PAGE_NUMBER = 1;
    private static int MAX_NUMBER_PER_PAGE = 10;

    private int pageNo;
    private int beginRow;
    private int numberPerPage;

    public Paging(int pageNo) {
        this.setPageNo(pageNo);
        this.setNumberPerPage();
        this.setBeginRow();
    }

    private void setPageNo(int pageNo) {
        if (pageNo < 0) {
            this.pageNo = DEFAULT_PAGE_NUMBER;
            return;
        }

        this.pageNo = pageNo;
    }

    private void setNumberPerPage() {
        this.numberPerPage = MAX_NUMBER_PER_PAGE;
    }

    private void setBeginRow() {
        this.beginRow = this.numberPerPage * (this.pageNo - 1);
    }


}
