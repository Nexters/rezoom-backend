package com.nexters.rezoom.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * Created by JaeeonJin on 2018-08-18.
 */
public class RecentClickResumeDTO {
    private int resumeId;
    private String companyName;
    private String jobType;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp clickDate;

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

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

    public Timestamp getClickDate() {
        return clickDate;
    }

    public void setClickDate(Timestamp clickDate) {
        this.clickDate = clickDate;
    }
}
