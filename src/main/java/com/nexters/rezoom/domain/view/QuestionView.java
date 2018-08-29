package com.nexters.rezoom.domain.view;

public class QuestionView {

    // questionId, title, content, hashtags
    public interface Simple {}

    // resume.companyName, resume.jobType
    public interface Search extends  Simple {}

    public interface Request {}

    public interface Response {}
}
