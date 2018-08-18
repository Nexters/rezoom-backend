package com.nexters.rezoom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * Created by JaeeonJin on 2018-08-19.
 */
public class ResumeDeadlineDTO {
    private String companyName;
    private String jobType;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Timestamp deadline;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }
}
