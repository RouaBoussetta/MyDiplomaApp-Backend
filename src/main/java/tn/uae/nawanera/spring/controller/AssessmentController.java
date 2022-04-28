package tn.uae.nawanera.spring.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tn.uae.nawanera.spring.entities.Assessment;
import tn.uae.nawanera.spring.entities.Notice;
import tn.uae.nawanera.spring.services.AssessmentService;
 
@RestController
@RequestMapping("/api/project/assessment")
public class AssessmentController {
	@Autowired
	AssessmentService assessmentservice;
	
	
	@PreAuthorize("hasAuthority('TRAINER')")
	@PostMapping(value = "/add-assessment/intern=/{intern}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Assessment addAssessment(@RequestPart String a,@RequestParam(value = "signature", required = true) MultipartFile signature,@RequestParam(value = "stamp", required = true) MultipartFile stamp,@PathVariable("intern") int intern)  {
		
		Assessment assessment=new Assessment();
		ObjectMapper objectMapper= new ObjectMapper();
		try {
            assessment=objectMapper.readValue(a,Assessment.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
	return	assessmentservice.addAssessment(assessment, signature, stamp ,  intern);

		 
	}

	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN') or hasAuthority('HR_MANAGER')")
	@GetMapping(value = "/display-assessment-details/{id}")
	@ResponseBody
	public Assessment  displayassessmentDetailsById(@PathVariable("id")int id) {

		return assessmentservice.displayAssessmentById(id);
	}
	
	@PreAuthorize("hasAuthority('TRAINER') or hasAuthority('INTERN') or hasAuthority('HR_MANAGER')")
	@GetMapping(value = "/display-assessments-by-intern/{id}")
	@ResponseBody
	public List<Assessment>  displayassessmentsByIntern(@PathVariable("id")int id) {

		return assessmentservice.displayAssessmentByIntern(id);
	}
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR') ")
	@GetMapping(value = "/display-all-assessments")
	@ResponseBody
	public List<Assessment>  displayAllAssessments( ) {

		return assessmentservice.retrieveAllAssessments();
	}
	
	@PreAuthorize("hasAuthority('INTERN') ")
	@GetMapping(value = "/display-intern-assessments")
	@ResponseBody
	public List<Assessment>  displayInternAssessments( ) {

		return assessmentservice.retrieveInternAssessement();
	}
	
	@PreAuthorize("hasAuthority('TRAINER') ")
	@GetMapping(value = "/display-trainer-assessments")
	@ResponseBody
	public List<Assessment>  displayTrainerAssessments( ) {

		return assessmentservice.retrieveTrainerAssessement();
	}
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping(value = "/display-Conclusive-assessment")
	@ResponseBody
	public List<Assessment>  displayConclusiveAssessment( )   {

		return assessmentservice.retrieveAllConclusive();
	}
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping(value = "/display-Inconclusive-assessment")
	@ResponseBody
	public List<Assessment>  displayInconclusiveAssessment( )   {

		return assessmentservice.retrieveAllInconclusive();
	}
	
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping(value = "/count-Conclusive-assessment")
	@ResponseBody
	public int countConclusiveAssessment( )   {

		return assessmentservice.retrieveAllConclusive().size();
	}
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping(value = "/count-Inconclusive-assessment")
	@ResponseBody
	public int countInconclusiveAssessment( )   {

		return assessmentservice.retrieveAllInconclusive().size();
	}
	
	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@GetMapping(value = "/display-assessments-by-notice/{notice}")
	@ResponseBody
	public List<Assessment>  displayAssessmentByNotice(@PathVariable("notice")Notice notice) {

		return assessmentservice.displayAssessmentsByNotice(notice);
	}
	
	@PreAuthorize("hasAuthority('TRAINER')")
	@DeleteMapping("/delete-assessment/{id}")  
	public void deleteAssessment(@PathVariable("id") int id)   
	{  
		assessmentservice.removeAssessment(id);
	}


	@PreAuthorize("hasAuthority('TRAINER')")

	@PutMapping("/update-project-assessment/{id}")
	public Assessment update(@PathVariable("id") int id,@RequestBody Assessment a)   {

		return assessmentservice.update(a, id);
	}
}
