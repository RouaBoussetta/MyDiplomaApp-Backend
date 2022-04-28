package tn.uae.nawanera.spring.entities.skillassessment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
	private int totalQuestions = 0;
	private int correctQuestions = 0;
	private int scoreIntern=0;
	

	
	public void addAnswer(Boolean isCorrect, int questions, int score) {
		 
		totalQuestions=questions;
		if (Boolean.TRUE.equals(isCorrect)) {
			correctQuestions++;
		}
		
		scoreIntern=score;

	}
}
