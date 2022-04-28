package tn.uae.nawanera.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uae.nawanera.spring.entities.Meeting;
import tn.uae.nawanera.spring.repositories.MeetingRepository;

@Service
public class MeetingService implements IMeetingService{
	@Autowired
	MeetingRepository meetingRepository;
	@Override
	public List<Meeting> retreiveMeetings() {
 
		return meetingRepository.findAll();
	}

}
