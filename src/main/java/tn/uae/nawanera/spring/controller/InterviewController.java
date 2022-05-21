package tn.uae.nawanera.spring.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.uae.nawanera.spring.entities.Interview;
import tn.uae.nawanera.spring.payload.response.MessageResponse;
import tn.uae.nawanera.spring.services.IinterviewService;

@RestController
@RequestMapping("/api/interview")
public class InterviewController {

	@Autowired
	IinterviewService iInterviewService;

	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@PostMapping(value = "/plannify/{idapp}")
	@ResponseBody
	public ResponseEntity<MessageResponse> plannifyInterview(@RequestBody Interview interview, @PathVariable ("idapp")int idapp) throws IOException, GeneralSecurityException {
		if (interview == null) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: please add values!"));
		}

		iInterviewService.planifyInterview(interview,idapp);

		return ResponseEntity.ok(new MessageResponse("Interview successfully plannified!"));
	}

	
	@PreAuthorize("hasAuthority('ADMINISTRATOR') ")
	@GetMapping("/retreiveAllInterviews")
	public List<Interview> getAllInteriews() {
		return iInterviewService.retreiveInterviews();
	}
	
	
	@PreAuthorize("hasAuthority('HR_MANAGER')")
	@DeleteMapping("/delete-interview/{id}")  
	public void delete (@PathVariable("id") int id)   
	{  
		iInterviewService.rejectInterview(id);
	}
	
	
	@PreAuthorize("hasAuthority('HR_MANAGER') or hasAuthority('INTERN') ")
	@GetMapping("/retreive-interview-by-application/{id}")
	public Interview getInteriewByApplicationId(@PathVariable("id") int id) {
		return iInterviewService.getInterviewByApplication(id);
	}
	
	

}
