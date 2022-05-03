package tn.uae.nawanera.spring.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uae.nawanera.spring.entities.Answer;
import tn.uae.nawanera.spring.entities.Question;
import tn.uae.nawanera.spring.entities.SkillAssessment;
import tn.uae.nawanera.spring.repositories.AnswerRepository;
import tn.uae.nawanera.spring.repositories.QuestionRepository;
import tn.uae.nawanera.spring.repositories.SkillAssessmentRepository;

@Service
public class QuestionService implements IQuestionService {

	@Autowired
	QuestionRepository questionrepository;
	@Autowired
	AnswerRepository answerrepository;

	@Autowired
	AnswerService answerSerice;

	@Autowired
	SkillAssessmentRepository skrepository;

	@Override
	public Question createQuestion(Question q, int skillAssessment) {

		SkillAssessment sk = skrepository.findById(skillAssessment);

		q.setSkillAssessment(sk);
		int count = questionrepository.countBySkillAssessment(q.getSkillAssessment());

		q.setQuestionorder(count + 1);
		q.setCreatedAt(LocalDateTime.now());

		return questionrepository.save(q);

	}
	
	@Override
	public Question createQuestion(Question q, String title) {

		SkillAssessment sk = skrepository.findByTitle(title);

		q.setSkillAssessment(sk);
		int count = questionrepository.countBySkillAssessment(q.getSkillAssessment());

		q.setQuestionorder(count + 1);
		q.setCreatedAt(LocalDateTime.now());

		return questionrepository.save(q);

	}

	@Override
	public Question update(int id, Question question) {

		Question q = questionrepository.findById(id);
		if (!question.getText().equals(q.getText()))
			q.setText(question.getText());

		if (question.getQuestionScore() != q.getQuestionScore())
			q.setQuestionScore(question.getQuestionScore());

		if (!question.getCorrectAnswer().equals(q.getCorrectAnswer()))
			q.setCorrectAnswer(question.getCorrectAnswer());

		return questionrepository.save(q);

	}

	@Override
	public List<Question> getAllQuestions() {

		return questionrepository.findAll();
	}

	@Override
	public void removeQuestion(int id) {
		Question question = questionrepository.findById(id);
		answerSerice.removeAnswerByQuestion(id);
		questionrepository.delete(question);

	}

	@Override
	public Question getQuestionById(int id) {

		return questionrepository.findById(id);
	}
	
	@Override
	public Question getQuestionByText(String text) {

		return questionrepository.findByText(text);
	}

	@Override
	public int countQuestionsInSkillAssessment(int skillAssessment) {
		SkillAssessment sk = skrepository.findById(skillAssessment);

		List<Question> questions = questionrepository.findBySkillAssessment(sk);

		return questions.size();
	}

	@Override
	public List<Question> getQuestionByskillAssessment(int skillAssessment) {
		SkillAssessment sk = skrepository.findById(skillAssessment);

		return questionrepository.findBySkillAssessment(sk);
	}

	@Override
	public void setCorrectAnswer(Question q, Answer a) {
		q.setCorrectAnswer(a);
		questionrepository.save(q);

	}
	
	@Override
	public void setCorrectAnswer(String q, String a) {
		
		Answer answer=answerrepository.findByText(a);
		Question question=questionrepository.findByText(q);
		question.setCorrectAnswer(answer);
		questionrepository.save(question);

	}

	@Override
	public Answer getCorrectAnswer(Question question) {
		return question.getCorrectAnswer();
	}

	@Override
	public Boolean checkIsCorrectAnswer(int q, int answerId) {

		Question question = questionrepository.getById(q);

		if (question.getCorrectAnswer().getId() == answerId)

			return true;
		else
			return false;
	}

}
