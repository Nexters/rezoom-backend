package com.nexters.rezoom.domain;

import java.util.ArrayList;
import java.util.List;

public class QuestionList {
	private int resumeId;
//	private int questionId;
	//private int userId;
	List<Question> list;
	
	public QuestionList() {
		list=new ArrayList<Question>();
	}

	public int getResumeId() {
		return resumeId;
	}

	public void setResumeId(int resumeId) {
		this.resumeId = resumeId;
	}


	public List<Question> getList() {
		return list;
	}

	public void setList(List<Question> list) {
		this.list = list;
	}


	
	
}
