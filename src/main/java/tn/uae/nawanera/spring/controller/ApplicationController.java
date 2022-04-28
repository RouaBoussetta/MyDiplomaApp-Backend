package tn.uae.nawanera.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.User;
import tn.uae.nawanera.spring.entities.Vacancy;
import tn.uae.nawanera.spring.payload.response.MessageResponse;
import tn.uae.nawanera.spring.services.IApplicationService;
import tn.uae.nawanera.spring.services.ISkillAssessmentService;
import tn.uae.nawanera.spring.services.IVacancyService;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {
	@Autowired
	IApplicationService iapplicationService;
	
	
	@Autowired
	IVacancyService iVacancyService;
	@Autowired
	ISkillAssessmentService iSkillAssessmentService;
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@GetMapping(value = "getAllInternByVacancy/{idvacancy}")
	@ResponseBody
	public List<User> getAllInternByVacancy(@PathVariable("idvacancy") int idvacancy) {

		return iapplicationService.getAllInternByVacancy(idvacancy);
	}

	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@GetMapping(value = "get-vacancies-By-intern/{idintern}")
	@ResponseBody
	public List<Vacancy> getVacanciesByIntern(@PathVariable("idintern") int idintern) {

		return iapplicationService.findVacanciesByIntern(idintern);
	}

	@PreAuthorize("hasAuthority('INTERN')")

	@PostMapping("/apply/{idvacancy}")
	public ResponseEntity<MessageResponse> apply(Application app, @RequestParam(value = "file", required = true) MultipartFile file,
			@PathVariable("idvacancy") int idvacancy) {

		iapplicationService.apply(app, file, idvacancy);

		return ResponseEntity.ok(new MessageResponse("Application successfully Registred!"));
	}

	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PutMapping("/accept-application/{idapp}")
	public Application acceptApplication(@PathVariable("idapp") int idapp) {

		return iapplicationService.acceptApplication(idapp);
	}
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@DeleteMapping("/decline-application/{idapp}")
	public void declineApplication(@PathVariable("idapp") int idapp) {

		 iapplicationService.declineApplication(idapp);
	}
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@GetMapping(value = "getAllApplicationsByVacancy/{idvacancy}")
	@ResponseBody
	public List<Application> getAllApplicationsByVacancy(@PathVariable("idvacancy") int idvacancy) {

		Vacancy v=iVacancyService.getVacancyById(idvacancy);
		return iapplicationService.findApplicationsByVacancy(v);
	}
	
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@GetMapping(value = "get-application/{id}")
	@ResponseBody
	public Application  getApplicationDetails (@PathVariable("id") int id ) {

	return iapplicationService.findApplicationById(id);
	}
	
	@PreAuthorize("hasAuthority('INTERN')")
	@GetMapping("/retreive-all-intern-skillAssessments")
	public List<Application> getAllOwnSkillAssessments() {
		return iapplicationService.retreiveApplicantSkillAssessments();
	}

}
