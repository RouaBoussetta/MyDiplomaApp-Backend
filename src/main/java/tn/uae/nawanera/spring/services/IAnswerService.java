package tn.uae.nawanera.spring.services;

import java.util.List;

import tn.uae.nawanera.spring.entities.Answer;
import tn.uae.nawanera.spring.entities.Question;

public interface IAnswerService {
 
	Answer addAnswerQuestion(Answer a, int question);




	List<Answer> getAllUserAnswers(int user);

	List<Answer> getAllAnswers();

	void removeAnswer(int id);

	Answer getAnswerById(int id);

	List<Answer> getAnswersInQuestion(int question);

	void removeCorrectAnswer(int q);

	int countAnswersInQuestion(Question q);

	List<Answer> findAnswersByQuestion(Question question);

	Answer update(int id, Answer answer);

	void removeAnswerByQuestion(int id);

	Answer addAnswerQuestion(Answer a, String question);




	Answer getAnswerByText(String string);

 
}
