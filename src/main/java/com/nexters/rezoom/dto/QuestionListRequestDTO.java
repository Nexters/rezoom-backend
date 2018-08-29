package com.nexters.rezoom.dto;

import com.nexters.rezoom.domain.Question;

import java.util.List;

public class QuestionListRequestDTO {
	private int resumeId;
	private List<Question> questions;

	public int getResumeId() {
		return resumeId;
	}

	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
}
