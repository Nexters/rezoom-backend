package com.nexters.rezoom.coverletter.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * Created by momentjin@gmail.com on 2019-03-22
 * Github : http://github.com/momentjin
 **/

@Embeddable
public class Deadline {

    @Getter
    @Column(name = "deadline")
    private LocalDateTime deadline;

    public Deadline() {
        this.deadline = null;
    }

    public Deadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public boolean isExpired() {
        if (deadline == null)
            return false;

        return deadline.isBefore(LocalDateTime.now());
    }

    public long getRemainingHours() {
        return LocalDateTime.now().until(this.deadline, ChronoUnit.HOURS);
    }

    public long getRemainingDays() {
        return LocalDateTime.now().until(this.deadline, ChronoUnit.DAYS);
    }

    public static Deadline now() {
        return new Deadline(LocalDateTime.now());
    }

    @Override
    public String toString() {
        if (deadline == null) return "no deadline";
        return this.deadline.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deadline deadline1 = (Deadline) o;
        return this.deadline.equals(deadline1.deadline);
    }

}
