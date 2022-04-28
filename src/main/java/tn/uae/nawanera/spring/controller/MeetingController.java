package tn.uae.nawanera.spring.controller;

import java.util.List;

import javax.annotation.security.PermitAll;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.uae.nawanera.spring.entities.Meeting;
import tn.uae.nawanera.spring.services.MeetingService;

@RestController
@RequestMapping("/api/meeting")
public class MeetingController {
	@Autowired
	MeetingService meetingService;
	@PermitAll
	@GetMapping("/get-all-meetings")  
	public List<Meeting> getAllLikes()   
	{  
		return meetingService.retreiveMeetings();
	}  
}
