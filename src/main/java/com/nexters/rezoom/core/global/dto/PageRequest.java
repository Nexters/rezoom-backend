package com.nexters.rezoom.core.global.dto;

import org.springframework.data.domain.Sort;

/**
 * Created by momentjin@gmail.com on 2019-10-07
 * Github : http://github.com/momentjin
 */
public class PageRequest {

    private static int DEFAULT_SIZE = 10;
    private static int MAX_SIZE = 20;

    private int page;
    private int size;
    private Sort.Direction direction;
    private Sort sort;

    public PageRequest() {
        page = 1;
        size = DEFAULT_SIZE;
        direction = Sort.Direction.DESC;
    }

    public void setPage(int page) {
        this.page = page <= 0 ? 1 : page;
    }

    public void setSize(int size) {
        this.size = size > MAX_SIZE ? DEFAULT_SIZE : size;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public org.springframework.data.domain.PageRequest of() {
        if (this.sort != null)
            return org.springframework.data.domain.PageRequest.of(page - 1, size, sort);

        return org.springframework.data.domain.PageRequest.of(page - 1, size, direction, "createdAt");
    }
}
