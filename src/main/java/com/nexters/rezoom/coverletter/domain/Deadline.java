package com.nexters.rezoom.coverletter.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Created by momentjin@gmail.com on 2019-03-22
 * Github : http://github.com/momentjin
 **/

@Embeddable
public class Deadline {

    @Column(name = "deadline")
    private LocalDateTime deadline;

    private final static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm";

    public Deadline() {
        this.deadline = null;
    }

    public Deadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public Deadline(String datetimeStr) {
        if (datetimeStr == null || datetimeStr.isEmpty()) {
            this.deadline = null;
            return;
        }

        this.deadline = this.convertLocalDateTime(datetimeStr);
    }

    /**
     * Domain Rule
     * - 현재 시간 기준, 마감일 초과 여부 검사
     * @return 마감일 경과했으면 true
     */
    public boolean isExpired() {
        if (deadline == null) return false;
        return deadline.isBefore(LocalDateTime.now());
    }

    private LocalDateTime convertLocalDateTime(String datetimeStr) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
            return LocalDateTime.parse(datetimeStr, formatter);
        } catch (Exception e) {
            // do not anything
            return null;
        }
    }


    public static Deadline now() {
        return new Deadline(LocalDateTime.now());
    }

    public long getRemainingHours() {
        return LocalDateTime.now().until(this.deadline, ChronoUnit.HOURS);
    }

    public long getRemainingDays() {
        return LocalDateTime.now().until(this.deadline, ChronoUnit.DAYS);
    }

    @Override
    public String toString() {
        if (deadline == null) return "no deadline";
        return this.deadline.toString();
    }
<<<<<<< HEAD
=======

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deadline deadline1 = (Deadline) o;
        return this.deadline.equals(deadline1.getDeadline());
    }

<<<<<<< HEAD
    public long getRemainingHours() {
        return LocalDateTime.now().until(this.deadline, ChronoUnit.HOURS);
    }

    public long getRemainingDays() {
        return LocalDateTime.now().until(this.deadline, ChronoUnit.DAYS);
    }
>>>>>>> 5b2d019... Add Notification api & scheduler
=======
>>>>>>> 6ca043b... Fix notification API erros
}
