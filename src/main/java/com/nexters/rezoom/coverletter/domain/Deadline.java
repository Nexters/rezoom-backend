package com.nexters.rezoom.coverletter.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by momentjin@gmail.com on 2019-03-22
 * Github : http://github.com/momentjin
 **/

@Embeddable
public class Deadline {

    @Column(name = "deadline")
    private LocalDateTime deadline;

    private final static String DATETIME_FORMAT = "yyyy-MM-dd'T'MM:mm";

    public Deadline() {
        this.deadline = null;
    }

    public Deadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Deadline(String deadlineStr) {
        if (deadlineStr == null || deadlineStr.isEmpty()) {
            this.deadline = null;
            return;
        }

        // TODO : 예외처리가 필요하다.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
        this.deadline = LocalDateTime.parse(deadlineStr, formatter);
    }

    /**
     * 현재 시간 기준, 마감일 초과 여부 검사
     * @return 마감일 경과했으면 true
     */
    public boolean isExpired() {
        if (deadline == null) return false;

        return deadline.isBefore(LocalDateTime.now());
    }

    @Override
    public String toString() {
        if (deadline == null) return "no deadline";
        return this.deadline.toString();
    }
}
