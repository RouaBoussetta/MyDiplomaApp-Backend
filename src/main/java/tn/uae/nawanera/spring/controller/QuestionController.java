package tn.uae.nawanera.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.uae.nawanera.spring.entities.Answer;
import tn.uae.nawanera.spring.entities.Question;
import tn.uae.nawanera.spring.services.AnswerService;
import tn.uae.nawanera.spring.services.QuestionService;

@RestController
@RequestMapping("/api/skill-assessment/question")

public class QuestionController {
	@Autowired
	QuestionService questionservice;
	@Autowired
	AnswerService answerService;
	
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PostMapping("/new-question-of/{sk}")
	public String addQuestion(@RequestBody Question question, @PathVariable("sk") int sk) {

 		questionservice.createQuestion(question, sk);

 		return "the question has been added successfuly ";

	}

	
	
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PutMapping("/{qId}/set-correct-answer/{answer_id}")
	public void setCorrectAnswer(@PathVariable("qId")int qId,@PathVariable("answer_id")int answerId) {

		Question question = questionservice.getQuestionById(qId);
		Answer answer = answerService.getAnswerById(answerId);
		questionservice.setCorrectAnswer(question, answer);
	}
	
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@GetMapping("/retrieve-all-questions")
	@ResponseBody

	public List<Question> getquestions() {

 
		return questionservice.getAllQuestions();
	}

	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@GetMapping("/retrieve-question-details/{idQuestion}")
	@ResponseBody

	public Question getQuestion(@PathVariable("idQuestion") int idQuestion) {

 			return   questionservice.getQuestionById(idQuestion);
		 
	}

	@PreAuthorize("hasAuthority('HR_MANAGER') or hasAuthority('INTERN')")
	@GetMapping("/retrieve-skillAssessment-questions/{idsk}")
	@ResponseBody

	public List<Question> getSkillAssessmentQuestions(@PathVariable("idsk") int idsk) {

 		return questionservice.getQuestionByskillAssessment(idsk);
	}
	
	@PreAuthorize("permitAll()")
	@GetMapping("/count-skillAssessment-questions/{idsk}")
	@ResponseBody
	public int countSkillAssessmentQuestions(@PathVariable("idsk") int idsk) {

		return questionservice.countQuestionsInSkillAssessment(idsk);
	}

	@PreAuthorize("hasAuthority('HR_MANAGER')")

	@PutMapping("/update-question/{id}")
	public Question updateQuestion(@PathVariable("id") int id,@RequestBody Question q)   {

		return questionservice.update(id,q);
	}
	
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@DeleteMapping("/delete-question/{idQuestion}")
	public String removeQuestion(@PathVariable("idQuestion") int idQuestion) {
		 

		questionservice.removeQuestion(idQuestion);

		return "The question has been removed successfuly";
	}
	
	


}
