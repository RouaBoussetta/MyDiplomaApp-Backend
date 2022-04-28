package tn.uae.nawanera.spring.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.uae.nawanera.spring.entities.Application;
import tn.uae.nawanera.spring.entities.Interview;
import tn.uae.nawanera.spring.repositories.ApplicationRepository;
import tn.uae.nawanera.spring.repositories.InterviewRepository;
import tn.uae.nawanera.spring.repositories.UserRepository;
import tn.uae.nawanera.spring.repositories.VacancyRepository;
 
@Service
public class InterviewService implements IinterviewService{
	
	@Autowired
	InterviewRepository interviewRepository;
	@Autowired
	ApplicationRepository applicationRepository;
	
	@Autowired
	VacancyRepository vacancyRepository;
	@Autowired
	UserRepository userRepository;
	@Override
	public Interview planifyInterview(Interview interview ,int idapp) {
		
		Application app=applicationRepository.findById(idapp);
		
		interview.setApplication(app);
		interview.setCreatedAt(LocalDateTime.now());
		
		interviewRepository.save(interview);
		app.setInterviewPlanned(true);
		applicationRepository.save(app);
		return interview;
	}
	@Override
	public List<Interview> retreiveInterviews() {
		
		return interviewRepository.findAll();
	}
	 

	

}
