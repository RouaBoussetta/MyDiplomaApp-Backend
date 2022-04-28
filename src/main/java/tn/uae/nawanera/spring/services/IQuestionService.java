package tn.uae.nawanera.spring.services;

import java.util.List;

import tn.uae.nawanera.spring.entities.Answer;
import tn.uae.nawanera.spring.entities.Question;

public interface IQuestionService {
 
	Question createQuestion(Question q, int skillAssessment);

	List<Question> getAllQuestions();

	void removeQuestion(int id);

	Question getQuestionById(int id);

	int countQuestionsInSkillAssessment(int skillAssessment);

	List<Question> getQuestionByskillAssessment(int skillAssessment);

	 

	void setCorrectAnswer(Question q, Answer a);

	Answer getCorrectAnswer(Question question);

 
	Boolean checkIsCorrectAnswer(int question, int answerId);

	Question update(int id, Question question);

}
