package com.nexters.rezoom.coverletter.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

/**
 * Created by momentjin@gmail.com on 2019-03-22
 * Github : http://github.com/momentjin
 **/

@Embeddable
public class Deadline {

    @Column(name = "deadline")
    private LocalDateTime deadline;

    public Deadline() {
    }

    public Deadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    /**
     * 현재 일시 기준, 마감일 초과 여부 검사
     *
     * @return 마감일 경과했으면 true
     */
    public boolean isExpired() {
        return deadline.isBefore(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return this.deadline.toString();
    }
}
