package com.nexters.rezoom.domain;

/**
 * Created by JaeeonJin on 2018-08-19.
 */
public class ResumeStatisticsSummary {
    private int resumeSize;
    private int pass;
    private int nonPass;
    private int nonSubmit;

    public int getResumeSize() {
        return resumeSize;
    }

    public void setResumeSize(int resumeSize) {
        this.resumeSize = resumeSize;
    }

    public int getPass() {
        return pass;
    }

    public void setPass(int pass) {
        this.pass = pass;
    }

    public int getNonPass() {
        return nonPass;
    }

    public void setNonPass(int nonPass) {
        this.nonPass = nonPass;
    }

    public int getNonSubmit() {
        return nonSubmit;
    }

    public void setNonSubmit(int nonSubmit) {
        this.nonSubmit = nonSubmit;
    }
}
