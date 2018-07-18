package com.nexters.rezoom.domain;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Resume implements Serializable {

    private int resumeId;
    private String userId;
    private String date;
    private int passFail;
    private String companyName;
    private String year;
    private List<Question> questionList;

    public int getResumeId() {
        return resumeId;
    }

    public void setResumeId(int resumeId) {
        this.resumeId = resumeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPassFail() {
        return passFail;
    }

    public void setPassFail(int passFail) {
        this.passFail = passFail;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}