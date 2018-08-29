package com.nexters.rezoom.domain.view;

public class ResumeView {

    // companyName, jobType
    public interface Simple {}

    // companyName, jobType, deadline
    public interface Deadline extends Simple {}

    // companyName, jobType, clickDate
    public interface ClickDate extends Deadline {}

}
