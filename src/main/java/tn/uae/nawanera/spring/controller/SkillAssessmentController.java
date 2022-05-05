package tn.uae.nawanera.spring.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
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

import tn.uae.nawanera.spring.entities.SkillAssessment;
import tn.uae.nawanera.spring.entities.skillassessment.Response;
import tn.uae.nawanera.spring.entities.skillassessment.Result;
import tn.uae.nawanera.spring.services.ISkillAssessmentService;

@RestController
@RequestMapping("/api/skill-assessment")
public class SkillAssessmentController {

	@Autowired
	ISkillAssessmentService iSkillAssessmentService;

	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PostMapping(value = "/create-skill-assessment")
	@ResponseBody
	public SkillAssessment createSkillAssessment(@RequestBody SkillAssessment skillAssessment)   {
	 


		iSkillAssessmentService.save(skillAssessment);

		return skillAssessment;
	}

	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping("/retreiveAllskillAssessments")
	public List<SkillAssessment> getAllSkillAssessments() {
		return iSkillAssessmentService.retreiveAll();
	}

	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@GetMapping("/retreive-all-own-skillAssessments")
	public List<SkillAssessment> getAllOwnSkillAssessments() {
		return iSkillAssessmentService.retreiveOwnSkillAssessments();
	}
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping("/retreiveAllPublishedskillAssessments")
	public List<SkillAssessment> retrieveAllPublishedSkillAssessment() {
		return iSkillAssessmentService.retrieveAllPublished();
	}

	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping("/retrieveSkillAssessmentsByUser/{iduser}")
	public List<SkillAssessment> retrieveSkillAssessmentsByUser(@PathVariable("iduser") int iduser) {
		return iSkillAssessmentService.retrieveAllByUser(iduser);
	}
	
	@PreAuthorize("hasAuthority('HR_MANAGER') or hasAuthority('COMPANY') or hasAuthority('INTERN')")
	@GetMapping("/retrieveSkillAssessment-details/{id}")
	public SkillAssessment retrieveSkillAssessment (@PathVariable("id") int id) {
		return iSkillAssessmentService.find(id);
	}

	@PreAuthorize("hasAuthority('HR_MANAGER')")

	@PutMapping("/Update-SkillAssessment/{id}")
	public SkillAssessment updateSkillAssessment(@PathVariable("id") int id,@RequestBody SkillAssessment sk)   {

		return iSkillAssessmentService.update(id,sk);
	}
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PutMapping("/assign-skill-assessment-to-intern/{sa}/{intern}")
	public void assignSAToIntern(@PathVariable("sa") int sa, @PathVariable("intern") int intern) throws GeneralSecurityException, IOException {

		iSkillAssessmentService.assignSAToIntern(sa, intern);

	}
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PutMapping("/assign-skill-assessment-to-internusername/{sa}/{intern}")
	public void assignSAToIntern(@PathVariable("sa") String sa, @PathVariable("intern") String intern) throws GeneralSecurityException, IOException {

		iSkillAssessmentService.assignSAToIntern(sa, intern);

	}
	
	@PreAuthorize("hasAuthority('INTERN')")
	@PostMapping(value = "/{saId}/submitAnswers")
	public Result startTest(@PathVariable("saId") int saId, @RequestBody List<Response> answers) {
		SkillAssessment sa = iSkillAssessmentService.find(saId);
		return iSkillAssessmentService.checkInternAnswers(sa, answers);
	}

	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PutMapping("/publish-SkillAssessment/{sk}")
	public void publishSkillAssessment(@PathVariable("sk") int sk) {

		iSkillAssessmentService.publishSkillAssessment(sk);
	}
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PutMapping("/publish-SkillAssessment-title/{sk}")
	public void publishSkillAssessment(@PathVariable("sk") String sk) {

		iSkillAssessmentService.publishSkillAssessment(sk);
	}
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@DeleteMapping("/delete-SkillAssessment/{sk}")
	public void deleteSkillAssessment(@PathVariable("sk") int sk) {

		
		iSkillAssessmentService.deleteSkillAssessment(sk);
	}

	

	
}
