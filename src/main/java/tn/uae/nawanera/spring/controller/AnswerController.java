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
import tn.uae.nawanera.spring.services.AnswerService;
import tn.uae.nawanera.spring.services.QuestionService;

@RestController
@RequestMapping("/api/skill-assessment/question/answers")
public class AnswerController {
	@Autowired
	QuestionService questionservice;

	@Autowired
	AnswerService answerservice;

	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PostMapping("/add-answer-to-question/{q}")
	public String addAnswerToQuestion(@RequestBody Answer answer, @PathVariable("q") int question) {

		
		answerservice.addAnswerQuestion(answer, question);

		return "The answer to question " + question + " of '" + answer.getQuestion().getSkillAssessment().getTitle()
				+ "' is well recorded   ";
	}
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PostMapping("/add-answer-to-question-text/{q}")
	public String addAnswerToQuestion(@RequestBody Answer answer, @PathVariable("q") String question) {

		
		answerservice.addAnswerQuestion(answer, question);

		return "The answer to question " + question + " of '" + answer.getQuestion().getSkillAssessment().getTitle()
				+ "' is well recorded   ";
	}

	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@GetMapping("/retrieve-all-answers_of/{user}")
	@ResponseBody

	public List<Answer> getAllUserAnswer(@PathVariable("user") int user) {

 
		return answerservice.getAllUserAnswers(user);

	}
	
	@PreAuthorize("hasAuthority('HR_MANAGER') or hasAuthority('INTERN')")

	@GetMapping("/retrieve-all-question-answers/{q}")
	@ResponseBody

	public List<Answer> getQuestionAnswers(@PathVariable("q") int q) {

 
		return answerservice.getAnswersInQuestion(q);

	}
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@DeleteMapping("/remove-answer-of-question/{idquestion}")
	public String removeAnswerOfQuestion(@PathVariable("idquestion") int idquestion){

		answerservice.removeCorrectAnswer(idquestion);
		answerservice.removeAnswer(idquestion);
		return "Response was successfuly removed  ";
	}

	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@DeleteMapping("/remove-answer/{idanswer}")
	public String removeAnswer(@PathVariable("idanswer") int idanswer){

		 answerservice.removeAnswer(idanswer);
		return "Response was successfuly removed  ";
	}

	
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")

	@PutMapping("/update-answer/{id}")
	public Answer updateAnswer(@PathVariable("id") int id,@RequestBody Answer a)   {

		return answerservice.update(id,a);
	}
	
	
	


	
	
	 
}
