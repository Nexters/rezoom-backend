package com.nexters.rezoom.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.nexters.rezoom.domain.view.QuestionView;
import com.nexters.rezoom.domain.view.ResumeView;

import java.sql.Timestamp;
import java.util.List;

public class Resume {
    private int resumeId;
    private String username;
    private int applicationYear;
    private String applicationType;

    @JsonView({ResumeView.Simple.class, QuestionView.Search.class})
    private String companyName;

    private String halfType;

    @JsonView({ResumeView.Simple.class, QuestionView.Search.class})
    private String jobType;

    private int finishFlag; // 지원 여부

    private int passFlag; // 합격 여부

    @JsonView(ResumeView.Deadline.class)
    @JsonFormat(pattern="yyyy-MM-dd HH")
    private Timestamp deadline; // 지원 마감일

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Timestamp createDate;

    @JsonIgnore
    @JsonView(ResumeView.ClickDate.class)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp clickDate;

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getApplicationYear() {
        return applicationYear;
    }

    public void setApplicationYear(int applicationYear) {
        this.applicationYear = applicationYear;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getHalfType() {
        return halfType;
    }

    public void setHalfType(String halfType) {
        this.halfType = halfType;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public int getFinishFlag() {
        return finishFlag;
    }

    public void setFinishFlag(int finishFlag) {
        this.finishFlag = finishFlag;
    }

    public int getPassFlag() {
        return passFlag;
    }

    public void setPassFlag(int passFlag) {
        this.passFlag = passFlag;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getClickDate() {
        return clickDate;
    }

    public void setClickDate(Timestamp clickDate) {
        this.clickDate = clickDate;
    }

}