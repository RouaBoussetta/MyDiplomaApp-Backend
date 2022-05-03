package tn.uae.nawanera.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uae.nawanera.spring.entities.Answer;
import tn.uae.nawanera.spring.entities.Question;
import tn.uae.nawanera.spring.repositories.AnswerRepository;
import tn.uae.nawanera.spring.repositories.QuestionRepository;
import tn.uae.nawanera.spring.repositories.SkillAssessmentRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;

@Service
public class AnswerService implements IAnswerService {

	@Autowired
	AnswerRepository answerrepository;
	@Autowired
	QuestionRepository questionrepository;
	@Autowired
	UserRepository userrepository;
	@Autowired
	SkillAssessmentRepository skrepository;
	
	@Override
	public Answer addAnswerQuestion(Answer a, int question) {

		Question q = questionrepository.findById(question) ;
		a.setQuestion(q);
		return answerrepository.save(a);
	}

	@Override
	public Answer addAnswerQuestion(Answer a, String question) {

		Question q = questionrepository.findByText(question) ;
		a.setQuestion(q);
		return answerrepository.save(a);
	}
	
	@Override
	public List<Answer> getAllUserAnswers(int user) {


		return answerrepository.findAll();
	}

	@Override
	public List<Answer> getAllAnswers() {

		return answerrepository.findAll();
	}


	
	@Override
	public void removeAnswer(int id) {
		Answer a = answerrepository.findById(id) ;
		answerrepository.delete(a);
		 

	}
	
	@Override
	public void removeAnswerByQuestion(int id) {
		
		Question q=questionrepository.findById(id) ;
		List<Answer> a = answerrepository.findByQuestion(q ) ;
		answerrepository.deleteAll(a);
		 

	}
	
	

	@Override
	public Answer getAnswerById(int id) {
		return answerrepository.findById(id) ;
	}
	
	@Override
	public Answer getAnswerByText(String string) {
 
		return answerrepository.findByText(string) ;
	}

	@Override
	public List<Answer> getAnswersInQuestion(int question) {
		Question q = questionrepository.findById(question) ;

		return answerrepository.findByQuestion(q);
	}

	@Override
	public void removeCorrectAnswer(int idquestion) {
		Question q = questionrepository.findById(idquestion) ;
		q.setCorrectAnswer(null);
		questionrepository.save(q);

	}

	 

	@Override
	public int countAnswersInQuestion(Question question) {
		return answerrepository.countByQuestion(question);
	}
	
	@Override
	public List<Answer> findAnswersByQuestion(Question question) {
		return answerrepository.findByQuestion(question);
	}
	
	
 
	
	private Answer updateAndSaveAnswer(Answer answer, Question question, int count) {
		answer.setAnswerorder(count + 1);
		answer.setQuestion(question);
		return  addAnswerQuestion(answer,question.getId());
	}

	@Override
	public Answer update(int id,Answer answer) {
		
		Answer 	a=answerrepository.findById(id) ;
		if(!answer.getText().equals(a.getText())) 
			a.setText(answer.getText());
		
		 
		
		return answerrepository.save(a);

	}

 

	 
	
	
	 
}
