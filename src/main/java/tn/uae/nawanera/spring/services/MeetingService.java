package tn.uae.nawanera.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tn.uae.nawanera.spring.entities.Answer;
import tn.uae.nawanera.spring.entities.Meeting;
import tn.uae.nawanera.spring.repositories.MeetingRepository;

@Service
public class MeetingService implements IMeetingService{
	@Autowired
	MeetingRepository meetingRepository;
	
	@Autowired
	IMeetingService imeetingService;
	
	
	@Override
	public List<Meeting> retreiveMeetings() {
 
		return meetingRepository.findAll();
	}

	
	/*
 	@PostMapping("/meeting")
	public String createmeeting(@RequestBody Meeting meeting ) {

		
 		imeetingService.

		return "The answer to question " + question + " of '" + answer.getQuestion().getSkillAssessment().getTitle()
				+ "' is well recorded   ";
	}*/
	
}
