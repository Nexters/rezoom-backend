package com.nexters.rezoom.dto;

import com.nexters.rezoom.domain.ResumeStatisticsSummary;

/**
 * Created by JaeeonJin on 2018-08-19.
 */
public class ResumeStatisticsDTO {
    private ResumeStatisticsDetail pass;
    private ResumeStatisticsDetail nonPass;
    private ResumeStatisticsDetail submit;
    private ResumeStatisticsDetail nonSubmit;

    public ResumeStatisticsDetail getSubmit() {
        return submit;
    }

    public void setSubmit(ResumeStatisticsDetail submit) {
        this.submit = submit;
    }

    public ResumeStatisticsDetail getPass() {
        return pass;
    }

    public void setPass(ResumeStatisticsDetail pass) {
        this.pass = pass;
    }

    public ResumeStatisticsDetail getNonPass() {
        return nonPass;
    }

    public void setNonPass(ResumeStatisticsDetail nonPass) {
        this.nonPass = nonPass;
    }

    public ResumeStatisticsDetail getNonSubmit() {
        return nonSubmit;
    }

    public void setNonSubmit(ResumeStatisticsDetail nonSubmit) {
        this.nonSubmit = nonSubmit;
    }

    private enum PASS_TYPE { 합격, 불합격, 제출, 미제출 }

    public ResumeStatisticsDTO (ResumeStatisticsSummary summary) {
        final int resumeSize = summary.getResumeSize();
        final int pass = summary.getPass();
        final int nonPass = summary.getNonPass();
        final int submit = summary.getSubmit();
        final int nonSubmit = summary.getNonSubmit();

        this.pass = new ResumeStatisticsDetail(PASS_TYPE.합격.name(), pass, calculateRatio(resumeSize, pass));
        this.nonPass = new ResumeStatisticsDetail(PASS_TYPE.불합격.name(), nonPass, calculateRatio(resumeSize, nonPass));
        this.submit = new ResumeStatisticsDetail(PASS_TYPE.제출.name(), submit, calculateRatio(resumeSize, submit));
        this.nonSubmit = new ResumeStatisticsDetail(PASS_TYPE.미제출.name(), nonSubmit, calculateRatio(resumeSize, nonSubmit));
    }

    private static class ResumeStatisticsDetail {
        private String title; // 합격, 불합격, 미제출
        private int resumeNum; // 각 이력서 갯수
        private double ratio; // 전체 이력서 중 차지하는 비율

        public ResumeStatisticsDetail(String title, int resumeNum, double ratio) {
            this.title = title;
            this.resumeNum = resumeNum;
            this.ratio = ratio;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getResumeNum() {
            return resumeNum;
        }

        public void setResumeNum(int resumeNum) {
            this.resumeNum = resumeNum;
        }

        public double getRatio() {
            return ratio;
        }

        public void setRatio(double ratio) {
            this.ratio = ratio;
        }
    }

    private static double calculateRatio(int resumeSize, int targetSize) {
        return Math.round( ((double)targetSize / (double)resumeSize)  * 100 * 100d) / 100d ;
    }
}
