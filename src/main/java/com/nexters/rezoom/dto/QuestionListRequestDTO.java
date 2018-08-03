package com.nexters.rezoom.dto;

import java.util.List;

public class QuestionListRequestDTO {
	private int resumeId;
	private List<QuestionDTO> questions;

	public int getResumeId() {
		return resumeId;
	}

	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}

	public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}
}
